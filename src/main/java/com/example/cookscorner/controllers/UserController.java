package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.response.UserResponseDTO;
import com.example.cookscorner.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookscorner")
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
