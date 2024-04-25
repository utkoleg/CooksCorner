package com.example.cookscorner.services;

import com.example.cookscorner.dto.user.UserResponseDTO;
import com.example.cookscorner.entities.CustomResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ResponseEntity<CustomResponse> activateUser(String token);

    ResponseEntity<CustomResponse> sendPasswordResetEmail(String email);

    ResponseEntity<CustomResponse> updatePassword(String token, String newPassword);

    ResponseEntity<CustomResponse> updateBio(String bio, HttpSession session);

    ResponseEntity<CustomResponse> updateImage(MultipartFile image, HttpSession session);

    UserResponseDTO getUser(UUID id);

    ResponseEntity<CustomResponse> toggleFollowUser(UUID id, HttpSession session);

    List<UserResponseDTO> getUsers();

    UserResponseDTO getProfile(HttpSession session);

    ResponseEntity<CustomResponse> changeNameSurname(String name, String surname, HttpSession session);

    void updateUserSubscriptionStatus(HttpSession session, boolean isSubscribed);
}
