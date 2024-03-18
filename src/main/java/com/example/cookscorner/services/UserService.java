package com.example.cookscorner.services;

import com.example.cookscorner.dto.response.UserResponseDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    String activateUser(String token);

    void sendPasswordResetEmail(String email);

    String updatePassword(String token, String newPassword);

    String updateBio(String bio, HttpSession id);

    UUID updateImage(MultipartFile image, HttpSession id);

    UserResponseDTO getUser(UUID id);

    UserResponseDTO followUser(UUID id, HttpSession userId);

    List<UserResponseDTO> getUsers();

    UserResponseDTO unfollowUser(UUID userToUnFollowId, HttpSession userId);

    UserResponseDTO getProfile(HttpSession session);

    UserResponseDTO changeNameSurname(String name, String surname, HttpSession session);
}
