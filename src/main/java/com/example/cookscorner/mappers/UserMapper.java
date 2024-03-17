package com.example.cookscorner.mappers;

import com.example.cookscorner.dto.response.UserResponseDTO;
import com.example.cookscorner.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper implements Function<User, UserResponseDTO> {

    private final RecipeMapper recipeMapper;
    @Override
    public UserResponseDTO apply(User user) {
        return new UserResponseDTO(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getImageUrl(),
                user.getBio(),
                user.getRecipes().stream().map(recipeMapper).toList(),
                user.getSavedRecipes().stream().map(recipeMapper).toList(),
                user.getLikedRecipes().stream().map(recipeMapper).toList(),
                user.getFollowers().stream().map(User::getId).collect(Collectors.toList()),
                user.getFollowing().stream().map(User::getId).collect(Collectors.toList()),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }
}
