package com.example.cookscorner.mappers;

import com.example.cookscorner.dto.ingredient.IngredientResponseDTO;
import com.example.cookscorner.dto.recipe.RecipeResponseDTO;
import com.example.cookscorner.entities.Ingredient;
import com.example.cookscorner.entities.Recipe;
import com.example.cookscorner.util.ObjectMapperUtils;
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
                recipe.getIngredients().stream().map(ObjectMapperUtils::fromIngredientToIngredientResponseDTO).collect(Collectors.toList()),
                recipe.getAuthor(),
                recipe.getComments().stream().map(ObjectMapperUtils::fromCommentToCommentResponseDTO).collect(Collectors.toList()),
                recipe.getRating()
        );
    }
}
