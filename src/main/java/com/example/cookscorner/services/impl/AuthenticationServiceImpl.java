package com.example.cookscorner.services.impl;

import com.example.cookscorner.config.JwtService;
import com.example.cookscorner.dto.authentication.AuthenticationRequest;
import com.example.cookscorner.dto.register.RegisterRequest;
import com.example.cookscorner.dto.authentication.AuthenticationResponse;
import com.example.cookscorner.entities.CustomResponse;
import com.example.cookscorner.entities.User;
import com.example.cookscorner.enums.Role;
import com.example.cookscorner.exceptions.MessagingException;
import com.example.cookscorner.exceptions.UserAlreadyExistsException;
import com.example.cookscorner.exceptions.UserNotFoundException;
import com.example.cookscorner.repositories.UserRepository;
import com.example.cookscorner.services.AuthenticationService;
import com.example.cookscorner.services.EmailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<CustomResponse> register(RegisterRequest request) {
        var existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.isEnabled()) {
                throw new UserAlreadyExistsException("User already exists");
            } else {
                resendVerificationEmail(user);
                CustomResponse response = new CustomResponse(HttpStatus.OK, "Verification email sent");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        var newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(false)
                .name(request.getName())
                .surname(request.getSurname())
                .build();
        newUser.generateActivationToken();
        userRepository.save(newUser);
        sendVerificationEmail(newUser);

        var jwtToken = jwtService.generateToken(newUser);
        CustomResponse response = new CustomResponse(HttpStatus.OK, "User registered with token: " + jwtToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void sendVerificationEmail(User user) throws MessagingException {
        String verificationUrl = "http://localhost:8080/cookscorner/auth/activate?token=" + user.getActivationToken();
        emailService.sendVerificationEmail(
                user.getEmail(),
                "Email Verification",
                emailService.buildEmail(user.getUsername(),
                        verificationUrl, "email"));
    }

    private void resendVerificationEmail(User user) throws MessagingException{
        user.generateActivationToken();
        userRepository.save(user);
        sendVerificationEmail(user);
    }

    @Override
    public ResponseEntity<CustomResponse> authenticate(AuthenticationRequest request, HttpSession session) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("username",request.getUsername()));
        var jwtToken = jwtService.generateToken(user);
        session.setAttribute("authorizedUser", user);
        session.setAttribute("authorizedUserId", user.getId());
        return new ResponseEntity<>(new CustomResponse(HttpStatus.OK, "Authenticated with token: " + jwtToken), HttpStatus.OK);
    }

}
