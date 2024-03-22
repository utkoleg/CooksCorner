package com.example.cookscorner.mappers;

import com.example.cookscorner.dto.ingredient.IngredientResponseDTO;
import com.example.cookscorner.dto.recipe.RecipeResponseDTO;
import com.example.cookscorner.entities.Ingredient;
import com.example.cookscorner.entities.Recipe;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RecipeMapper implements Function<Recipe, RecipeResponseDTO> {
    @Override
    public RecipeResponseDTO apply(Recipe recipe) {
        return new RecipeResponseDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getDifficulty(),
                recipe.getCategory(),
                recipe.getPreparationTime(),
                recipe.getImageUrl(),
                recipe.getIngredients().stream().map(this::getIngredientResponseDTO).collect(Collectors.toList()),
                recipe.getAuthor()
        );
    }

    public IngredientResponseDTO getIngredientResponseDTO(Ingredient ingredient) {
        return new IngredientResponseDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getMeasurement()
        );
    }
}
