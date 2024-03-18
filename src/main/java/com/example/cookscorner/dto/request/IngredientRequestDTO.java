package com.example.cookscorner.dto.request;

import jakarta.validation.constraints.Min;
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

    @Min(value = -1, message = "Quantity must be a positive number")
    private String quantity;

    @NotBlank(message = "Measurement is required")
    private String measurement;
}
