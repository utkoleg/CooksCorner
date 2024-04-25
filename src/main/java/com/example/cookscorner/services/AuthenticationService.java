package com.example.cookscorner.services;

import com.example.cookscorner.dto.authentication.AuthenticationRequest;
import com.example.cookscorner.dto.authentication.AuthenticationResponse;
import com.example.cookscorner.dto.register.RegisterRequest;
import com.example.cookscorner.entities.CustomResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;


public interface AuthenticationService {
    ResponseEntity<CustomResponse> register(RegisterRequest request);

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request, HttpSession session) throws ExpiredJwtException;

}
