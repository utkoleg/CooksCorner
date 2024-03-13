package com.example.cookscorner.dto.response;

import com.example.cookscorner.entities.Ingredient;
import com.example.cookscorner.enums.Difficulty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDTO {
    private UUID id;

    private String name;

    private String description;

    private Difficulty difficulty;

    //private String difficulty;

    private String category;

    private int preparationTime;

    private String imageUrl;

    private List<Ingredient> ingredients;
}
