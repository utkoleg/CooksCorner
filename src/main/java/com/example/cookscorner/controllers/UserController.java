package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.user.UserResponseDTO;
import com.example.cookscorner.entities.CustomResponse;
import com.example.cookscorner.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookscorner")
@CrossOrigin("*")
@Tag(name = "User controller", description = "Endpoints for user management")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(summary = "Get all users", responses = {
            @ApiResponse(responseCode = "200", description = "List of users",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))),
    })
    public List<UserResponseDTO> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get user details", responses = {
            @ApiResponse(responseCode = "200", description = "User details",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserResponseDTO getUser(@PathVariable(name = "id") UUID id){
        return userService.getUser(id);
    }

    @GetMapping("/profile")
    @Operation(summary = "Get user profile", responses = {
            @ApiResponse(responseCode = "200", description = "User profile details",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found or not authenticated")
    })
    public UserResponseDTO getUserProfile(HttpSession session) {
        return userService.getProfile(session);
    }

    @PostMapping("/users/{userToFollowId}")
    @Operation(summary = "Follow or unfollow user", responses = {
            @ApiResponse(responseCode = "200", description = "User followed successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<CustomResponse> followUser(@PathVariable(name = "userToFollowId") UUID userToFollowId,
                                                     HttpSession session){
        return userService.toggleFollowUser(userToFollowId, session);
    }
}
