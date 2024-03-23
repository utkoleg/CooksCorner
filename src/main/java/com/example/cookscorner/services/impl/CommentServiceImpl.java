package com.example.cookscorner.services.impl;

import com.example.cookscorner.entities.Comment;
import com.example.cookscorner.entities.Recipe;
import com.example.cookscorner.entities.User;
import com.example.cookscorner.repositories.CommentRepository;
import com.example.cookscorner.repositories.RecipeRepository;
import com.example.cookscorner.repositories.UserRepository;
import com.example.cookscorner.services.CommentService;
import com.example.cookscorner.services.ElasticSearchService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.OptionalDouble;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ElasticSearchService elasticSearchService;
    @Override
    public ResponseEntity<UUID> addComment(UUID recipeId, String commentText, HttpSession session, Double rating) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
        UUID userId = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment comment = Comment.builder()
                .username(user.getUsername())
                .imageUrl(user.getImageUrl())
                .commentText(commentText)
                .rating(rating)
                .build();

        commentRepository.save(comment);
        recipe.getComments().add(comment);
        recipeRepository.save(recipe);
        updateRating(recipe);
        return ResponseEntity.ok(comment.getId());
    }

    private void updateRating(Recipe recipe) {
        OptionalDouble average = recipe.getComments().stream()
                .mapToDouble(Comment::getRating)
                .average();

        if (average.isPresent()) {
            double avgRating = BigDecimal.valueOf(average.getAsDouble())
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue();
            recipe.setRating(avgRating);
            recipeRepository.save(recipe);

            elasticSearchService.updateRecipe(recipe);
        } else {
            log.warn("No ratings to average for recipe with id: {}", recipe.getId());
        }
    }
}
