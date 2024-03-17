package com.example.cookscorner.services;

import com.example.cookscorner.dto.request.IngredientRequestDTO;
import com.example.cookscorner.dto.response.RecipeResponseDTO;
import com.example.cookscorner.entities.Recipe;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface RecipeService {
    List<RecipeResponseDTO> getRecipes();

//    UUID addRecipe(String name, String description, String difficulty, String category, String preparationTime, List<IngredientRequestDTO> ingredients, MultipartFile image);
//    UUID addRecipe(RecipeRequestDTO recipeRequestDTO);



    List<RecipeResponseDTO> getRecipesByCategory(String category);

    RecipeResponseDTO getRecipe(UUID id);

    UUID addRecipe(String name, String description, String difficulty, String category, String preparationTime, List<IngredientRequestDTO> ingredientRequestDTOs, MultipartFile image);

    UUID saveRecipeToUser(UUID recipeId, HttpSession userId);

    UUID likeRecipe(UUID recipeId, HttpSession session);
}
