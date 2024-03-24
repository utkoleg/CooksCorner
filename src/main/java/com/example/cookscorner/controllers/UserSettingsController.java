package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.user.UserResponseDTO;
import com.example.cookscorner.entities.CustomResponse;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookscorner/settings")
@CrossOrigin("*")
@Tag(name = "User's settings controller", description = "Endpoints for user's settings management")
public class UserSettingsController {
    private final UserService userService;

    @PutMapping("/change_name")
    @Operation(summary = "Change user's name", responses = {
            @ApiResponse(responseCode = "200", description = "Name changed successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<CustomResponse> changeNameSurname(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            HttpSession session
    ){
        return userService.changeNameSurname(name, surname, session);
    }
    @PostMapping("/update_bio")
    @Operation(summary = "Update user bio", responses = {
            @ApiResponse(responseCode = "200", description = "User bio updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<CustomResponse> updateBio(@RequestParam(name = "bio") String bio,
                                                    HttpSession session){
        return userService.updateBio(bio, session);
    }
    @PostMapping("/update_image")
    @Operation(summary = "Update user profile image", responses = {
            @ApiResponse(responseCode = "200", description = "User profile image updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<CustomResponse> updateImage(@RequestParam(name = "image") MultipartFile image,
                                                      HttpSession session){
        return userService.updateImage(image, session);
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
