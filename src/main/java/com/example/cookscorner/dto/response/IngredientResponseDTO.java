package com.example.cookscorner.dto.response;

import java.util.UUID;

public record IngredientResponseDTO(UUID id,

                                    String name,

                                    int quantity,

                                    String measurement) {
}
