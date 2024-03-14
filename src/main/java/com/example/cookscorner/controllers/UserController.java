package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.response.UserResponseDTO;
import com.example.cookscorner.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookscorner")
@CrossOrigin("*")
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

    @GetMapping("/{id}")
    @Operation(summary = "Get user details", responses = {
            @ApiResponse(responseCode = "200", description = "User details",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserResponseDTO getUser(@PathVariable(name = "id") UUID id){
        return userService.getUser(id);
    }

    @PostMapping("/{userToFollowId}")
    @Operation(summary = "Follow a user", responses = {
            @ApiResponse(responseCode = "200", description = "User followed successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserResponseDTO followUser(@PathVariable(name = "userToFollowId") UUID userToFollowId,
                                      @RequestParam(name = "userId") UUID userId){
        return userService.followUser(userToFollowId, userId);
    }

    @PostMapping("/{userToUnFollowId}/unfollow")
    @Operation(summary = "Unfollow a user", responses = {
            @ApiResponse(responseCode = "200", description = "User unfollowed successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserResponseDTO unfollowUser(@PathVariable(name = "userToUnFollowId") UUID userToUnFollowId,
                                        @RequestParam(name = "userId") UUID userId){
        return userService.unfollowUser(userToUnFollowId, userId);
    }
}
