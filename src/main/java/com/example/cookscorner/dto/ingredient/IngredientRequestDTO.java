package com.example.cookscorner.dto.ingredient;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequestDTO {
    @NotBlank(message = "Ingredient name is required")
    private String name;

    @NotBlank(message = "Ingredient quantity is required")
    private String quantity;

    @NotBlank(message = "Measurement is required")
    private String measurement;
}
