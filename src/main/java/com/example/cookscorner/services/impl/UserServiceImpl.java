package com.example.cookscorner.services.impl;

import com.example.cookscorner.dto.response.UserResponseDTO;
import com.example.cookscorner.entities.User;
import com.example.cookscorner.exceptions.ImageUploadException;
import com.example.cookscorner.mappers.UserMapper;
import com.example.cookscorner.repositories.UserRepository;
import com.example.cookscorner.services.EmailService;
import com.example.cookscorner.services.FileUploadService;
import com.example.cookscorner.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadService fileUploadService;
    private final UserMapper userMapper;

    @Value("${app.baseUrl}")
    private String baseUrl;

    @Override
    public String activateUser(String token) {
        User user = userRepository.findByActivationToken(token);
        if (user != null) {
            user.setEnabled(true);
            userRepository.save(user);
            return "User verified successfully";
        } else {
            return "Invalid token";
        }
    }

    public void sendPasswordResetEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);
        userRepository.save(user);

        String resetLink = baseUrl + "/auth/update-password?token=" + token;
        emailService.sendVerificationEmail(email, "Password Reset", emailService.buildEmail(user.getUsername(), resetLink, "password"));
    }

    public String updatePassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token);
        if (user != null && !user.getPasswordResetToken().isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setPasswordResetToken(null);
            userRepository.save(user);
            return "Password updated successfully";
        }
        return "Invalid or expired token";
    }

    @Override
    public String updateBio(String bio, HttpSession session) {
        UUID id = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (user != null) {
            user.setBio(bio);
            userRepository.save(user);
            return "Bio updated successfully";
        }
        return "User not found";
    }

    @Override
    public UUID updateImage(MultipartFile image, HttpSession session) {
        UUID id = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

        try {
            user.setImageUrl(fileUploadService.uploadFile(image));
        } catch (IOException e) {
            throw new ImageUploadException("Failed to upload image", e);
        }

        return userRepository.save(user).getId();
    }

    @Override
    public UserResponseDTO getUser(UUID id) {
        return userMapper
                .apply(userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("User not found")));
    }

    @Override
    public UserResponseDTO followUser(UUID userToFollowId, HttpSession session) {
        User userToFollow = userRepository.findById(userToFollowId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        User user = (User) session.getAttribute("authorizedUser");

        user.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(user);
        userRepository.save(userToFollow);
        userRepository.save(user);

        return userMapper.apply(userRepository.findById(user.getId()).orElseThrow());
    }

    @Override
    public UserResponseDTO unfollowUser(UUID userToUnFollowId, HttpSession session) {
        User userToFollow = userRepository.findById(userToUnFollowId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        User user = (User) session.getAttribute("authorizedUser");

        user.getFollowing().remove(userToFollow);
        userToFollow.getFollowers().remove(user);

        userRepository.save(user);
        userRepository.save(userToFollow);

        return userMapper.apply(userRepository.findById(user.getId()).orElseThrow());
    }

    @Override
    public UserResponseDTO getProfile(HttpSession session) {
        UUID id = (UUID) session.getAttribute("authorizedUserId");
        return userMapper.apply(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserResponseDTO changeNameSurname(String name, String surname, HttpSession session) {
        UUID id = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setName(name);
        user.setSurname(surname);

        return userMapper.apply(userRepository.save(user));
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        return userRepository.findAll().stream().map(userMapper).collect(Collectors.toList());
    }
}
