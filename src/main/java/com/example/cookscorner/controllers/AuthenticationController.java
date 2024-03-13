package com.example.cookscorner.controllers;

import com.example.cookscorner.services.AuthenticationService;
import com.example.cookscorner.dto.request.AuthenticationRequest;
import com.example.cookscorner.dto.request.RegisterRequest;
import com.example.cookscorner.dto.response.AuthenticationResponse;
import com.example.cookscorner.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/cookscorner/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/activate")
    public String verifyUser(@RequestParam("token") String token) {
        return userService.activateUser(token);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam(name = "email") String email) {
        userService.sendPasswordResetEmail(email);
        return "Password reset email sent";
    }

    @PostMapping("/update-bio")
    public String updateBio(@RequestParam(name = "bio") String bio,
                            @RequestParam(name = "id") UUID id){
        return userService.updateBio(bio, id);
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam(name = "token") String token, @RequestParam(name = "password") String newPassword) {
        return userService.updatePassword(token, newPassword);
    }

    @PostMapping("/update-image")
    public UUID updateImage(@RequestParam(name = "image") MultipartFile image,
                              @RequestParam(name = "id") UUID id){
        return userService.updateImage(image, id);
    }
}
