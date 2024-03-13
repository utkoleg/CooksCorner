package com.example.cookscorner.services;

import com.example.cookscorner.dto.request.AuthenticationRequest;
import com.example.cookscorner.dto.request.RegisterRequest;
import com.example.cookscorner.dto.response.AuthenticationResponse;
import com.example.cookscorner.entities.User;
import io.jsonwebtoken.ExpiredJwtException;


public interface AuthenticationService {
    public AuthenticationResponse register(RegisterRequest request);

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ExpiredJwtException;

}
