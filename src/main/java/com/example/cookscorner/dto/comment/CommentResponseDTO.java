package com.example.cookscorner.dto.comment;

import java.util.UUID;

public record CommentResponseDTO(
        UUID id,
        String username,
        String imageUrl,
        String commentText,
        Double rating
){}
