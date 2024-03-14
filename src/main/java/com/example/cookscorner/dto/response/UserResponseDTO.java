package com.example.cookscorner.dto.response;

import com.example.cookscorner.entities.Recipe;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private UUID id;

    private String username;

    private String email;

    private String imageUrl;

    private String bio;

    List<Recipe> recipes;

    List<Recipe> savedRecipes;

    List<UUID> followers;

    List<UUID> following;
}
