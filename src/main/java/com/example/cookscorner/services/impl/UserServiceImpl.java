package com.example.cookscorner.services.impl;

import com.example.cookscorner.dto.user.UserResponseDTO;
import com.example.cookscorner.entities.CustomResponse;
import com.example.cookscorner.entities.User;
import com.example.cookscorner.exceptions.ImageUploadException;
import com.example.cookscorner.exceptions.MessagingException;
import com.example.cookscorner.exceptions.UserNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CustomResponse> activateUser(String token) {
        User user = userRepository.findByActivationToken(token);
        if (user != null) {
            user.setEnabled(true);
            userRepository.save(user);
            return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "User activated successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse(HttpStatus.NOT_FOUND, "Error during user activation"), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<CustomResponse> sendPasswordResetEmail(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email" ,email));

        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);
        userRepository.save(user);

        String resetLink = baseUrl + "/auth/update-password?token=" + token;
        emailService.sendVerificationEmail(email, "Password Reset", emailService.buildEmail(user.getUsername(), resetLink, "password"));
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Password reset email sent"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponse> updatePassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token);
        if (user != null && !user.getPasswordResetToken().isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setPasswordResetToken(null);
            userRepository.save(user);
            return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Password updated successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CustomResponse(HttpStatus.BAD_REQUEST, "Invalid or expired token"), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<CustomResponse> updateBio(String bio, HttpSession session) {
        UUID id = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id", String.valueOf(id)));
        if (user != null) {
            user.setBio(bio);
            userRepository.save(user);
            return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Bio updated"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CustomResponse(HttpStatus.NOT_FOUND, "User not found"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<CustomResponse> updateImage(MultipartFile image, HttpSession session) {
        UUID id = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id", String.valueOf(id)));

        try {
            user.setImageUrl(fileUploadService.uploadFile(image));
        } catch (IOException e) {
            throw new ImageUploadException("Failed to upload image", e);
        }
        userRepository.save(user).getId();
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Image updated"), HttpStatus.OK);
    }

    @Override
    public UserResponseDTO getUser(UUID id) {
        return userMapper
                .apply(userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("id", String.valueOf(id))));
    }

    @Override
    public ResponseEntity<CustomResponse> toggleFollowUser(UUID userToToggleId, HttpSession session) {
        User userToToggle = userRepository.findById(userToToggleId)
                .orElseThrow(() -> new UserNotFoundException("id", String.valueOf(userToToggleId)));
        UUID userId = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("id", String.valueOf(userId)));

        if (user.getFollowing().contains(userToToggle)) {
            user.getFollowing().remove(userToToggle);
            userToToggle.getFollowers().remove(user);
            userRepository.save(user);
            userRepository.save(userToToggle);
            return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "User unfollowed"), HttpStatus.OK);
        } else {
            user.getFollowing().add(userToToggle);
            userToToggle.getFollowers().add(user);
            userRepository.save(user);
            userRepository.save(userToToggle);
            return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "User followed"), HttpStatus.OK);
        }
    }


    @Override
    public UserResponseDTO getProfile(HttpSession session) {
        UUID id = (UUID) session.getAttribute("authorizedUserId");
        return userMapper.apply(userRepository.findById(id).orElseThrow());
    }

    @Override
    public ResponseEntity<CustomResponse> changeNameSurname(String name, String surname, HttpSession session) {
        UUID id = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id", String.valueOf(id)));

        user.setName(name);
        user.setSurname(surname);

        userRepository.save(user);

        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Named changed"), HttpStatus.OK);
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        return userRepository.findAll().stream().map(userMapper).collect(Collectors.toList());
    }
}
