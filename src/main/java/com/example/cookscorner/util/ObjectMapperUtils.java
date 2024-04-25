package com.example.cookscorner.util;

import com.example.cookscorner.dto.comment.CommentResponseDTO;
import com.example.cookscorner.dto.ingredient.IngredientResponseDTO;
import com.example.cookscorner.dto.recipe.RecipeResponseDTO;
import com.example.cookscorner.dto.user.UserResponseDTO;
import com.example.cookscorner.entities.Comment;
import com.example.cookscorner.entities.Ingredient;
import com.example.cookscorner.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ObjectMapperUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> fromObjectToMap(Object object) {
        return objectMapper.convertValue(object, new TypeReference<>() {});
    }

    public static UserResponseDTO fromUserToUserResponseDTO(User user){
        return objectMapper.convertValue(user, UserResponseDTO.class);
    }
    public static CommentResponseDTO fromCommentToCommentResponseDTO(Comment comment){
        return objectMapper.convertValue(comment, CommentResponseDTO.class);
    }

    public static IngredientResponseDTO fromIngredientToIngredientResponseDTO(Ingredient ingredient){
        return objectMapper.convertValue(ingredient, IngredientResponseDTO.class);
    }

    public static RecipeResponseDTO fromObjectToRecipeResponseDTO(Map<String, Object> document) {
        UUID id = UUID.fromString((String) document.get("id"));
        String name = (String) document.get("name");
        String description = (String) document.get("description");
        String difficulty = (String) document.get("difficulty");
        String category = (String) document.get("category");
        String preparationTime = (String) document.get("preparationTime");
        String imageUrl = (String) document.get("imageUrl");
        String author = (String) document.get("author");
        List<IngredientResponseDTO> ingredients = objectMapper.convertValue(
                document.get("ingredients"),
                new TypeReference<>() {
                }
        );
        List<CommentResponseDTO> comments = objectMapper.convertValue(
                document.get("comments"),
                new TypeReference<>() {
                }
        );
        Double rating = (Double) document.get("rating");

        return new RecipeResponseDTO(id, name, description, difficulty, category, preparationTime, imageUrl, ingredients, author, comments, rating);
    }

    public static String getJson(Map<String, Object> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
