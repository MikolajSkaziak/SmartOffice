package com.example.smartofficeapi.services;

import com.example.smartofficeapi.config.JwtService;
import com.example.smartofficeapi.dto.AuthRequest;
import com.example.smartofficeapi.dto.AuthResponse;
import com.example.smartofficeapi.dto.RegisterRequest;
import com.example.smartofficeapi.entities.User;
import com.example.smartofficeapi.repository.interfaces.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldReturnToken_WhenRoleIsEmployee() {
        RegisterRequest request = new RegisterRequest("employee", "pass", "ROLE_EMPLOYEE");
        User savedUser = User.builder().username("employee").role(User.Role.ROLE_EMPLOYEE).build();

        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("mocked-jwt-token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        
        verify(userRepository).save(argThat(user -> user.getRole() == User.Role.ROLE_EMPLOYEE));
    }

    @Test
    void register_ShouldReturnToken_WhenRoleIsManager() {
        RegisterRequest request = new RegisterRequest("manager", "pass", "ROLE_MANAGER");

        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(jwtService.generateToken(any(User.class))).thenReturn("mocked-jwt-token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        verify(userRepository).save(argThat(user -> user.getRole() == User.Role.ROLE_MANAGER));
    }

    @Test
    void authenticate_ShouldReturnToken_WhenCredentialsAreValid() {
        AuthRequest request = new AuthRequest("user", "pass");
        User user = User.builder().username("user").build();

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("mocked-jwt-token");

        AuthResponse response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
