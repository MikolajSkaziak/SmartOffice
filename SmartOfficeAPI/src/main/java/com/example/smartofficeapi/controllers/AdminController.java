package com.example.smartofficeapi.controllers;

import com.example.smartofficeapi.entities.User;
import com.example.smartofficeapi.services.UserService;
import com.example.smartofficeapi.repository.JdbcStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final JdbcStatsRepository jdbcStatsRepository;

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Integer>> getUserStats() {
        return ResponseEntity.ok(jdbcStatsRepository.getUserRoleStats());
    }
}

