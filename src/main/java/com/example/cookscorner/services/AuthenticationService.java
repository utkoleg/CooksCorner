package com.example.cookscorner.services;

import com.example.cookscorner.dto.authentication.AuthenticationRequest;
import com.example.cookscorner.dto.register.RegisterRequest;
import com.example.cookscorner.dto.authentication.AuthenticationResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpSession;


public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request, HttpSession session) throws ExpiredJwtException;

}
