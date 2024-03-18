package com.example.cookscorner.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Difficulty is required")
    @Pattern(regexp = "Easy|Medium|Hard", message = "Difficulty must be one of the following: Easy, Medium, Hard")
    private String difficulty;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Preparation time is required")
    private String preparationTime;

    @Valid
    @Size(min = 1, message = "At least one ingredient is required")
    private List<IngredientRequestDTO> ingredients;

    @NotNull(message = "Image is required")
    private MultipartFile image;

    private String author;
}