package com.example.cookscorner.services;

import com.example.cookscorner.dto.ingredient.IngredientRequestDTO;
import com.example.cookscorner.dto.recipe.RecipeResponseDTO;
import com.example.cookscorner.entities.CustomResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface RecipeService {
    List<RecipeResponseDTO> getRecipes();

    List<RecipeResponseDTO> getRecipesByCategory(String category);

    RecipeResponseDTO getRecipe(UUID id);

    ResponseEntity<CustomResponse> addRecipe(String name, String description, String difficulty, String category, String preparationTime, List<IngredientRequestDTO> ingredientRequestDTOs, MultipartFile image, HttpSession session);

    ResponseEntity<CustomResponse> saveRecipeToUser(UUID recipeId, HttpSession userId);

    ResponseEntity<CustomResponse> likeRecipe(UUID recipeId, HttpSession session);
}
