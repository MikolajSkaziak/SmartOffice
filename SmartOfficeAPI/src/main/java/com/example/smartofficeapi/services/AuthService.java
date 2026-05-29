package com.example.smartofficeapi.services;

import com.example.smartofficeapi.config.JwtService;
import com.example.smartofficeapi.dto.AuthRequest;
import com.example.smartofficeapi.dto.AuthResponse;
import com.example.smartofficeapi.dto.RegisterRequest;
import com.example.smartofficeapi.entities.User;
import com.example.smartofficeapi.repository.interfaces.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User.Role userRole = User.Role.ROLE_EMPLOYEE;
        if (request.getRole() != null && request.getRole().equals("ROLE_MANAGER")) {
            userRole = User.Role.ROLE_MANAGER;
        }

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
