package com.example.cookscorner.services;

import com.example.cookscorner.dto.request.AuthenticationRequest;
import com.example.cookscorner.dto.request.RegisterRequest;
import com.example.cookscorner.dto.response.AuthenticationResponse;
import io.jsonwebtoken.ExpiredJwtException;


public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request) throws ExpiredJwtException;

}
