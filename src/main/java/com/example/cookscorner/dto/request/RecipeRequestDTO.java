package com.example.cookscorner.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequestDTO {
    private String name;
    private String description;
    private String difficulty;
    private String category;
    private String preparationTime;
    private List<IngredientRequestDTO> ingredients;
    private MultipartFile image;
}