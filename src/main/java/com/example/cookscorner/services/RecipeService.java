package com.example.cookscorner.services;

import com.example.cookscorner.dto.response.RecipeResponseDTO;
import com.example.cookscorner.entities.Ingredient;
import com.example.cookscorner.entities.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface RecipeService {
    List<Recipe> getRecipes();

    UUID addRecipe(String name, String description, String difficulty, String category, String preparationTime, List<Ingredient> ingredients, MultipartFile image);

    List<Recipe> getRecipesByCategory(String category);

    RecipeResponseDTO getRecipe(UUID id);
}
