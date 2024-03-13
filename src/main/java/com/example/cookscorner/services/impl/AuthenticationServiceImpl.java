package com.example.cookscorner.services.impl;

import com.example.cookscorner.config.JwtService;
import com.example.cookscorner.dto.request.AuthenticationRequest;
import com.example.cookscorner.dto.request.RegisterRequest;
import com.example.cookscorner.dto.response.AuthenticationResponse;
import com.example.cookscorner.enums.Role;
import com.example.cookscorner.entities.User;
import com.example.cookscorner.exceptions.UserAlreadyExistsException;
import com.example.cookscorner.repositories.UserRepository;
import com.example.cookscorner.services.AuthenticationService;
import com.example.cookscorner.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationResponse register(RegisterRequest request) {
        var existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.isEnabled()) {
                throw new UserAlreadyExistsException("User already exists");
            } else {
                // User exists but is not enabled, resend verification email
                resendVerificationEmail(user);
                return null;
            }
        }

        // User does not exist, create a new user
        var newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(false) // Set enabled to false initially
                .build();
        newUser.generateActivationToken();
        userRepository.save(newUser);
        sendVerificationEmail(newUser);

        // You might want to return a different response here since the user is not fully registered yet
        var jwtToken = jwtService.generateToken(newUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void sendVerificationEmail(User user) {
        String verificationUrl = "http://localhost:8080/cookscorner/auth/activate?token=" + user.getActivationToken();
        emailService.sendVerificationEmail(
                user.getEmail(),
                "Email Verification",
                emailService.buildEmail(user.getUsername(),
                        verificationUrl, "email"));
    }

    private void resendVerificationEmail(User user) {
        // You can regenerate the token or use the existing one
        user.generateActivationToken();
        userRepository.save(user);
        sendVerificationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
