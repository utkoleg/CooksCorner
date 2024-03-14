package com.example.cookscorner.services.impl;

import com.example.cookscorner.dto.request.IngredientRequestDTO;
import com.example.cookscorner.dto.response.RecipeResponseDTO;
import com.example.cookscorner.entities.Ingredient;
import com.example.cookscorner.entities.Recipe;
import com.example.cookscorner.enums.Difficulty;
import com.example.cookscorner.repositories.IngredientRepository;
import com.example.cookscorner.repositories.RecipeRepository;
import com.example.cookscorner.services.FileUploadService;
import com.example.cookscorner.services.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

//    @Override
//    public UUID addRecipe(String name, String description, String difficulty, String category,
//                          String preparationTime, List<IngredientRequestDTO> ingredientRequestDTOs, MultipartFile image) {
//
//        validateRecipeInput(name, description, difficulty, category, preparationTime, ingredientRequestDTOs);
//
//        List<Ingredient> ingredients = ingredientRequestDTOs.stream()
//                .map(this::convertToIngredient)
//                .collect(Collectors.toList());
//
//        Recipe recipe = null;
//        try {
//            recipe = Recipe.builder()
//                    .name(name)
//                    .description(description)
//                    .difficulty(Difficulty.valueOf(difficulty.toUpperCase()))
//                    .category(category)
//                    .preparationTime(Integer.parseInt(preparationTime))
//                    .ingredients(ingredients)
//                    .imageUrl(fileUploadService.uploadFile(image))
//                    .build();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return recipeRepository.save(recipe).getId();
//    }

        @Override
    public UUID addRecipe(String name, String description, String difficulty, String category,
                          String preparationTime, List<IngredientRequestDTO> ingredientRequestDTOs, MultipartFile image) {

        validateRecipeInput(name, description, difficulty, category, preparationTime, ingredientRequestDTOs);

        List<Ingredient> ingredients = ingredientRequestDTOs.stream()
                .map(this::convertToIngredient)
                .collect(Collectors.toList());

        ingredientRepository.saveAll(ingredients);
        Recipe recipe;
        try {
            recipe = Recipe.builder()
                    .name(name)
                    .description(description)
                    .difficulty(Difficulty.valueOf(difficulty.toUpperCase()))
                    .category(category)
                    .preparationTime(Integer.parseInt(preparationTime))
                    .ingredients(ingredients)
                    .imageUrl(fileUploadService.uploadFile(image))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return recipeRepository.save(recipe).getId();
    }

//    @Override
//    public UUID addRecipe(RecipeRequestDTO recipeRequestDTO) {
//
//        List<Ingredient> ingredients = recipeRequestDTO.getIngredients().stream()
//                .map(this::convertToIngredient)
//                .collect(Collectors.toList());
//
//        Recipe recipe = null;
//        try {
//            recipe = Recipe.builder()
//                    .name(recipeRequestDTO.getName())
//                    .description(recipeRequestDTO.getDescription())
//                    .difficulty(Difficulty.valueOf(recipeRequestDTO.getDifficulty().toUpperCase()))
//                    .category(recipeRequestDTO.getCategory())
//                    .preparationTime(Integer.parseInt(recipeRequestDTO.getPreparationTime()))
//                    .ingredients(ingredients)
//                    .imageUrl(fileUploadService.uploadFile(recipeRequestDTO.getImage()))
//                    .build();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return recipeRepository.save(recipe).getId();
//    }

    @Override
    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findAllByCategory(category);
    }

    @Override
    public RecipeResponseDTO getRecipe(UUID id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        return RecipeResponseDTO.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .category(recipe.getCategory())
                .description(recipe.getDescription())
                .difficulty(recipe.getDifficulty())
                .imageUrl(recipe.getImageUrl())
                .ingredients(recipe.getIngredients())
                .build();
    }

    private void validateRecipeInput(String name, String description, String difficulty, String category,
                                     String preparationTime, List<IngredientRequestDTO> ingredients) {
        // Check if any string parameter is null or empty
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be null or empty.");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Recipe description cannot be null or empty.");
        }
        if (difficulty == null || difficulty.isEmpty()) {
            throw new IllegalArgumentException("Recipe difficulty cannot be null or empty.");
        }
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Recipe category cannot be null or empty.");
        }

        // Validate the difficulty level
        try {
            Difficulty.valueOf(difficulty.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid difficulty level: " + difficulty);
        }

        // Check if preparation time is a valid integer
        try {
            int prepTime = Integer.parseInt(preparationTime);
            if (prepTime <= 0) {
                throw new IllegalArgumentException("Preparation time must be a positive integer.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid preparation time: " + preparationTime);
        }

        // Validate the ingredients list
        if (ingredients == null || ingredients.isEmpty()) {
            throw new IllegalArgumentException("Ingredients list cannot be null or empty.");
        }
        for (IngredientRequestDTO ingredient : ingredients) {
            if (ingredient.getName() == null || ingredient.getName().isEmpty()) {
                throw new IllegalArgumentException("Ingredient name cannot be null or empty.");
            }
            if (ingredient.getQuantity() <= 0) {
                throw new IllegalArgumentException("Ingredient quantity must be a positive number.");
            }
        }
    }

    public Ingredient convertToIngredient(IngredientRequestDTO ingredientRequestDTO) {
        return Ingredient.builder()
                .name(ingredientRequestDTO.getName())
                .quantity(ingredientRequestDTO.getQuantity())
                .measurement(ingredientRequestDTO.getMeasurement())
                .build();
    }
}
