package com.example.smartofficeapi.controllers;

import com.example.smartofficeapi.dto.ReservationRequest;
import com.example.smartofficeapi.entities.Reservation;
import com.example.smartofficeapi.entities.User;
import com.example.smartofficeapi.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    public ResponseEntity<Reservation> createReservation(
            @RequestBody ReservationRequest request,
            @AuthenticationPrincipal User user) {

        Reservation createdReservation = reservationService.createReservation(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    public ResponseEntity<List<Reservation>> getAllReservations(){
        List<Reservation> reservations = reservationService.getAllReservations();
        return  ResponseEntity.ok(reservations);
    }

}