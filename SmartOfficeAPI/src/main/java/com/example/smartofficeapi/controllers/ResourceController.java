package com.example.smartofficeapi.controllers;

import com.example.smartofficeapi.dto.ResourceCreationRequest;
import com.example.smartofficeapi.entities.OfficeResource;
import com.example.smartofficeapi.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_MANAGER')")
    public ResponseEntity<List<OfficeResource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<OfficeResource> createResource(@RequestBody ResourceCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceService.createResource(request));
    }
}


