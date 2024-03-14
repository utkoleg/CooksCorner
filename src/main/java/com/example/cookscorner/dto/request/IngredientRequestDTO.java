package com.example.cookscorner.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequestDTO {
    private String name;

    private int quantity;

    private String measurement;
}
