package com.example.cookscorner.dto.user;


import com.example.cookscorner.dto.recipe.RecipeResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResponseDTO(UUID id,

                              String username,

                              String email,

                              String imageUrl,

                              String bio,

                              List<RecipeResponseDTO> recipes,

                              List<RecipeResponseDTO> savedRecipes,

                              List<RecipeResponseDTO> likedRecipes,

                              List<UUID> followers,

                              List<UUID> following,

                              List<String> roles,

                              String name,

                              String surname) {
}
