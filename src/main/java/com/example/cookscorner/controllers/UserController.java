package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.response.UserResponseDTO;
import com.example.cookscorner.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookscorner")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable(name = "id") UUID id){
        return userService.getUser(id);
    }

    @PostMapping("/{id}")
    public UserResponseDTO followUser(@PathVariable(name = "id") UUID id,
                                      @RequestParam(name = "userId") UUID userId){
        return userService.followUser(id, userId);
    }
}
