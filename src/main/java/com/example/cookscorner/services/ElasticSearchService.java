package com.example.cookscorner.services;

import com.example.cookscorner.dto.response.IngredientResponseDTO;
import com.example.cookscorner.dto.response.RecipeResponseDTO;
import com.example.cookscorner.entities.Recipe;
import com.example.cookscorner.mappers.RecipeMapper;
import com.example.cookscorner.util.ObjectMapperUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticSearchService {
    private static final String CLASSPATH_SETTINGS = "classpath:settings/elasticsearch-settings.json";
    private static final String CLASSPATH_MAPPINGS = "classpath:mappings/elasticsearch-dynamic-mappings.json";

    private final RecipeMapper recipeMapper;
    private final RestHighLevelClient elasticsearchClient;
    private final ResourceLoader resourceLoader;

    @Value("${elasticsearch.index-names.recipe_index}")
    private String indexName;

    @PostConstruct
    public void createIndex() {
        log.info("creating index with name {}", indexName);
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        try {
            String jsonMappings = readFileAsString(resourceLoader.getResource(CLASSPATH_MAPPINGS).getURI());

            log.info("adding settings and mappings to index from classpath");
            request.mapping(jsonMappings, XContentType.JSON);
            request.settings(Settings.builder()
                    .loadFromPath(Paths.get(resourceLoader.getResource(CLASSPATH_SETTINGS).getURI()))
                    .build());

            elasticsearchClient.indices().create(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException e) {
            log.error(e.getLocalizedMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String readFileAsString(URI file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    private void sendAddRequestElastic(UUID id, String jsonData) {
        IndexRequest indexRequest = new IndexRequest(indexName)
                .id(id.toString())
                .source(jsonData, XContentType.JSON);

        log.info("handling response from elastic");
        try {
            elasticsearchClient.index(indexRequest, RequestOptions.DEFAULT).getId();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void saveRecipe(Recipe recipe) {
        Map<String, Object> map = ObjectMapperUtils.fromObjectToMap(recipe);
        log.info("Map: {}", map);

        sendAddRequestElastic(recipe.getId(), ObjectMapperUtils.getJson(map));
    }

    public List<Object> searchByField(String name) {
        log.info("creating a search request to index '{}',", indexName);
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        log.info("creating a match query");
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", name).fuzziness(Fuzziness.AUTO));

        sourceBuilder.query(boolQueryBuilder);
        searchRequest.source(sourceBuilder);

        try {
            log.info("handling response of match query");
            return Arrays.stream(elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT)
                            .getHits().getHits())
                    .map(SearchHit::getSourceAsMap)
//                    .map(this::convertMapToRecipeResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RecipeResponseDTO convertMapToRecipeResponseDTO(Map<String, Object> document) {
        UUID id = (UUID) document.get("id");
        String name = (String) document.get("name");
        String description = (String) document.get("description");
        String difficulty = (String) document.get("difficulty");
        String category = (String) document.get("category");
        String preparationTime = (String) document.get("preparationTime");
        String imageUrl = (String) document.get("imageUrl");
        List<IngredientResponseDTO> ingredients = ((List<Map<String, Object>>) document.get("ingredients"))
                .stream()
                .map(this::mapToIngredientResponseDTO)
                .collect(Collectors.toList());
        String author = (String) document.get("author");
        return new RecipeResponseDTO(id, name, description, difficulty, category,
                preparationTime, imageUrl, ingredients, author);
    }

    private IngredientResponseDTO mapToIngredientResponseDTO(Map<String, Object> ingredientMap) {
        return new IngredientResponseDTO(
                ((UUID) ingredientMap.get("id")),
                (String) ingredientMap.get("name"),
                (String) ingredientMap.get("quantity"),
                (String) ingredientMap.get("measurement")
        );
    }
}
