package com.example.cookscorner.services.impl;

import com.example.cookscorner.dto.ingredient.IngredientRequestDTO;
import com.example.cookscorner.dto.recipe.RecipeResponseDTO;
import com.example.cookscorner.entities.CustomResponse;
import com.example.cookscorner.entities.Ingredient;
import com.example.cookscorner.entities.Recipe;
import com.example.cookscorner.entities.User;
import com.example.cookscorner.exceptions.UserNotFoundException;
import com.example.cookscorner.mappers.RecipeMapper;
import com.example.cookscorner.repositories.IngredientRepository;
import com.example.cookscorner.repositories.RecipeRepository;
import com.example.cookscorner.repositories.UserRepository;
import com.example.cookscorner.services.ElasticSearchService;
import com.example.cookscorner.services.FileUploadService;
import com.example.cookscorner.services.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final FileUploadService fileUploadService;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final RecipeMapper recipeMapper;
    private final ElasticSearchService elasticSearchService;


    @Override
    public List<RecipeResponseDTO> getRecipes() {
        return recipeRepository.findAll().stream().map(recipeMapper).toList();
    }

    @Override
    public ResponseEntity<CustomResponse> addRecipe(String name, String description, String difficulty, String category,
                                                    String preparationTime, List<IngredientRequestDTO> ingredientRequestDTOs,
                                                    MultipartFile image, HttpSession session) {

        List<Ingredient> ingredients = ingredientRequestDTOs.stream()
                .map(this::convertToIngredient)
                .collect(Collectors.toList());

        ingredientRepository.saveAll(ingredients);
        Recipe recipe;
        UUID userId = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("id", String.valueOf(userId)));
        String author = user.getUsername();
        try {
            recipe = Recipe.builder()
                    .name(name)
                    .description(description)
                    .difficulty(difficulty.toUpperCase())
                    .category(category)
                    .preparationTime(preparationTime)
                    .ingredients(ingredients)
                    .imageUrl(fileUploadService.uploadFile(image))
                    .author(author)
                    .rating(0.0)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        user.getRecipes().add(recipe);
        recipeRepository.saveAndFlush(recipe);
        elasticSearchService.saveRecipe(recipe);
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Recipe added successfully"), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CustomResponse> saveRecipeToUser(UUID recipeId, HttpSession session) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Recipe not found with id: %s", recipeId)));

        UUID userId = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("id", String.valueOf(userId)));

        if (!user.getSavedRecipes().contains(recipe)) {
            user.getSavedRecipes().add(recipe);
            userRepository.save(user);
            log.info("Recipe saved");
            return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Recipe saved"), HttpStatus.OK);
        } else {
            user.getSavedRecipes().remove(recipe);
            userRepository.save(user);
            log.info("Recipe unsaved");
            return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Recipe unsaved"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<CustomResponse> likeRecipe(UUID recipeId, HttpSession session) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Recipe not found with id: %s", recipeId)));
        UUID userId = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("id", String.valueOf(userId)));

        if (user.getLikedRecipes().contains(recipe)) {
            user.getLikedRecipes().remove(recipe);
            userRepository.save(user);
            log.info("recipe unliked");
            return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Recipe unliked"), HttpStatus.OK);
        } else {
            user.getLikedRecipes().add(recipe);
            userRepository.save(user);
            log.info("recipe liked");
            return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Recipe liked"), HttpStatus.OK);
        }
    }

    @Override
    public List<RecipeResponseDTO> getRecipesByCategory(String category) {
        return recipeRepository.findAllByCategory(category).stream().map(recipeMapper).toList();
    }

    @Override
    public RecipeResponseDTO getRecipe(UUID id) {
        return recipeMapper.apply(recipeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe not found")));
    }

    public Ingredient convertToIngredient(IngredientRequestDTO ingredientRequestDTO) {
        return Ingredient.builder()
                .name(ingredientRequestDTO.getName())
                .quantity(ingredientRequestDTO.getQuantity())
                .measurement(ingredientRequestDTO.getMeasurement())
                .build();
    }
}