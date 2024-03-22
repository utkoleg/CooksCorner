package com.example.cookscorner.dto.ingredient;

import java.util.UUID;

public record IngredientResponseDTO(UUID id,

                                    String name,

                                    String quantity,

                                    String measurement) {
}
