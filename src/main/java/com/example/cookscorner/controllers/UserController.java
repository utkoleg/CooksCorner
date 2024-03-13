package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.response.UserResponseDTO;
import com.example.cookscorner.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookscorner")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get a user by ID")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class))})
    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable(name = "id") UUID id){
        return userService.getUser(id);
    }

    @Operation(summary = "Follow a user")
    @ApiResponse(responseCode = "200", description = "User followed successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class))})
    @PostMapping("/{id}")
    public UserResponseDTO followUser(@PathVariable(name = "id") UUID id,
                                      @RequestParam(name = "userId") UUID userId){
        return userService.followUser(id, userId);
    }
}
