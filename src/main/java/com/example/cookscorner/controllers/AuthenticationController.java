package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.request.AuthenticationRequest;
import com.example.cookscorner.dto.request.RegisterRequest;
import com.example.cookscorner.dto.response.AuthenticationResponse;
import com.example.cookscorner.services.AuthenticationService;
import com.example.cookscorner.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/cookscorner/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Authentication", description = "Endpoints for user authentication and account management")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;


    @PostMapping("/register")
    @Operation(summary = "Register a new user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate a user", responses = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request,
            HttpSession session
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request, session));
    }

    @GetMapping("/activate")
    @Operation(summary = "Activate a user account", responses = {
            @ApiResponse(responseCode = "200", description = "User account activated successfully"),
            @ApiResponse(responseCode = "404", description = "Activation token not found or invalid")
    })
    public String verifyUser(@RequestParam("token") String token) {
        return userService.activateUser(token);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Request a password reset", responses = {
            @ApiResponse(responseCode = "200", description = "Password reset email sent"),
            @ApiResponse(responseCode = "404", description = "Email not found")
    })
    public String resetPassword(@RequestParam(name = "email") String email) {
        userService.sendPasswordResetEmail(email);
        return "Password reset email sent";
    }

    @PostMapping("/update-bio")
    @Operation(summary = "Update user bio", responses = {
            @ApiResponse(responseCode = "200", description = "User bio updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public String updateBio(@RequestParam(name = "bio") String bio,
                            @RequestParam(name = "id") UUID id){
        return userService.updateBio(bio, id);
    }

    @PostMapping("/update-password")
    @Operation(summary = "Update user password", responses = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "404", description = "Reset token not found or invalid")
    })
    public String updatePassword(@RequestParam(name = "token") String token, @RequestParam(name = "password") String newPassword) {
        return userService.updatePassword(token, newPassword);
    }

    @PostMapping("/update-image")
    @Operation(summary = "Update user profile image", responses = {
            @ApiResponse(responseCode = "200", description = "User profile image updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UUID updateImage(@RequestParam(name = "image") MultipartFile image,
                              @RequestParam(name = "id") UUID id){
        return userService.updateImage(image, id);
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out a user", responses = {
            @ApiResponse(responseCode = "200", description = "User logged out successfully")
    })
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("User logged out.");
    }
}
