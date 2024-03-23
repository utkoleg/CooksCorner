package com.example.cookscorner.dto.recipe;


import com.example.cookscorner.dto.comment.CommentResponseDTO;
import com.example.cookscorner.dto.ingredient.IngredientResponseDTO;

import java.util.List;
import java.util.UUID;

public record RecipeResponseDTO(UUID id, String name,

                                String description,

                                String difficulty,

                                String category,

                                String preparationTime,

                                String imageUrl,

                                List<IngredientResponseDTO> ingredients,

                                String author,
                                List<CommentResponseDTO> comments,
                                Double rating) {
}
