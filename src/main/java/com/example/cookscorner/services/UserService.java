package com.example.cookscorner.services;

import com.example.cookscorner.dto.response.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    String activateUser(String token);

    void sendPasswordResetEmail(String email);

    String updatePassword(String token, String newPassword);

    String updateBio(String bio, UUID id);

    UUID updateImage(MultipartFile image, UUID id);

    UserResponseDTO getUser(UUID id);

    UserResponseDTO followUser(UUID id, UUID userId);
}
