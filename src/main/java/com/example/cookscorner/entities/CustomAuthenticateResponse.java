package com.example.cookscorner.entities;

import com.example.cookscorner.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomAuthenticateResponse {
    private HttpStatus status;
    private UserResponseDTO user;
}
