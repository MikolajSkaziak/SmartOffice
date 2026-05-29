package com.example.smartofficeapi.controllers;

import com.example.smartofficeapi.dto.AuthRequest;
import com.example.smartofficeapi.dto.AuthResponse;
import com.example.smartofficeapi.dto.RegisterRequest;
import com.example.smartofficeapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMyRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        String roles = auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(", "));
        return ResponseEntity.ok("User: " + auth.getName() + ", Roles: " + roles);
    }
}
