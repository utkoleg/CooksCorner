package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.authentication.AuthenticationRequest;
import com.example.cookscorner.dto.register.RegisterRequest;
import com.example.cookscorner.dto.authentication.AuthenticationResponse;
import com.example.cookscorner.entities.CustomAuthenticateResponse;
import com.example.cookscorner.entities.CustomResponse;
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

@RestController
@RequestMapping("/cookscorner/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Authentication controller", description = "Endpoints for user authentication and account management")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;


    @PostMapping("/register")
    @Operation(summary = "Register a new user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<CustomResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return authenticationService.register(request);
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
        return authenticationService.authenticate(request, session);
    }

    @GetMapping("/activate")
    @Operation(summary = "Activate a user account", responses = {
            @ApiResponse(responseCode = "200", description = "User account activated successfully"),
            @ApiResponse(responseCode = "404", description = "Activation token not found or invalid")
    })
    public ResponseEntity<CustomResponse> verifyUser(@RequestParam("token") String token) {
        return userService.activateUser(token);
    }

    @PostMapping("/reset_password")
    @Operation(summary = "Request a password reset", responses = {
            @ApiResponse(responseCode = "200", description = "Password reset email sent"),
            @ApiResponse(responseCode = "404", description = "Email not found")
    })
    public ResponseEntity<CustomResponse> resetPassword(@RequestParam(name = "email") String email) {
        return userService.sendPasswordResetEmail(email);
    }

    @PostMapping("/update_password")
    @Operation(summary = "Update user password", responses = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "404", description = "Reset token not found or invalid")
    })
    public ResponseEntity<CustomResponse> updatePassword(@RequestParam(name = "token") String token, @RequestParam(name = "password") String newPassword) {
        return userService.updatePassword(token, newPassword);
    }
}
