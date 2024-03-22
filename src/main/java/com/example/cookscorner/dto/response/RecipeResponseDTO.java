package com.example.cookscorner.dto.response;

import com.example.cookscorner.enums.Difficulty;

import java.util.List;
import java.util.UUID;

public record RecipeResponseDTO(UUID id, String name,

                                String description,

                                String difficulty,

                                String category,

                                String preparationTime,

                                String imageUrl,

                                List<IngredientResponseDTO> ingredients,

                                String author) {
}
