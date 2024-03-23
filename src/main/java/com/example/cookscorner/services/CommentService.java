package com.example.cookscorner.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CommentService {
    ResponseEntity<UUID> addComment(UUID recipeId, String commentText, HttpSession session, Double rating);
}
