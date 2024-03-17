package com.example.cookscorner.dto.response;


import java.util.List;
import java.util.UUID;

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
                              List<String> roles) {
}
