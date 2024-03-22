package com.example.cookscorner.util;

import com.example.cookscorner.dto.ingredient.IngredientResponseDTO;
import com.example.cookscorner.dto.recipe.RecipeResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ObjectMapperUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> fromObjectToMap(Object object) {
        return objectMapper.convertValue(object, new TypeReference<>() {});
    }

    public static RecipeResponseDTO fromObjectToRecipeResponseDTO(Map<String, Object> document) {
        UUID id = UUID.fromString((String) document.get("id"));
        String name = (String) document.get("name");
        String description = (String) document.get("description");
        String difficulty = (String) document.get("difficulty");
        String category = (String) document.get("category");
        String preparationTime = (String) document.get("preparationTime");
        String imageUrl = (String) document.get("imageUrl");
        String author = (String) document.get("author");
        List<IngredientResponseDTO> ingredients = objectMapper.convertValue(
                document.get("ingredients"),
                new TypeReference<List<IngredientResponseDTO>>() {}
        );

        return new RecipeResponseDTO(id, name, description, difficulty, category, preparationTime, imageUrl, ingredients, author);
    }

    public static String getJson(Map<String, Object> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}