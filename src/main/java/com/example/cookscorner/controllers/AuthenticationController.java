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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/cookscorner/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Operation(summary = "Register a new user")
    @ApiResponse(responseCode = "200", description = "User registered successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthenticationResponse.class))})
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @Operation(summary = "Authenticate a user")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthenticationResponse.class))})
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @Operation(summary = "Verify user account")
    @ApiResponse(responseCode = "200", description = "User verified successfully",
            content = {@Content(mediaType = "text/plain")})
    @GetMapping("/activate")
    public String verifyUser(@RequestParam("token") String token) {
        return userService.activateUser(token);
    }

    @Operation(summary = "Reset password")
    @ApiResponse(responseCode = "200", description = "Password reset email sent",
            content = {@Content(mediaType = "text/plain")})
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam(name = "email") String email) {
        userService.sendPasswordResetEmail(email);
        return "Password reset email sent";
    }

    @Operation(summary = "Update password")
    @ApiResponse(responseCode = "200", description = "Password updated successfully",
            content = {@Content(mediaType = "text/plain")})
    @PostMapping("/update-password")
    public String updatePassword(@RequestParam(name = "token") String token, @RequestParam(name = "password") String newPassword) {
        return userService.updatePassword(token, newPassword);
    }

    @Operation(summary = "Update bio")
    @ApiResponse(responseCode = "200", description = "Bio updated successfully",
            content = {@Content(mediaType = "text/plain")})
    @PostMapping("/update-bio")
    public String updateBio(@RequestParam(name = "bio") String bio,
                            @RequestParam(name = "id") UUID id){
        return userService.updateBio(bio, id);
    }

    @Operation(summary = "Update profile image")
    @ApiResponse(responseCode = "200", description = "Image updated successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UUID.class))})
    @PostMapping("/update-image")
    public UUID updateImage(@RequestParam(name = "image") MultipartFile image,
                              @RequestParam(name = "id") UUID id){
        return userService.updateImage(image, id);
    }
}
