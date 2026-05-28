package com.example.smartofficeapi.services;

import com.example.smartofficeapi.dto.ReservationRequest;
import com.example.smartofficeapi.entities.OfficeResource;
import com.example.smartofficeapi.entities.Reservation;
import com.example.smartofficeapi.entities.User;
import com.example.smartofficeapi.exceptions.ResourceConflictException;
import com.example.smartofficeapi.repository.interfaces.IReservationRepository;
import com.example.smartofficeapi.repository.interfaces.IResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final IReservationRepository reservationRepository;
    private final IResourceRepository resourceRepository;

    @Transactional
    public Reservation createReservation(ReservationRequest request, User requestingUser) {
        OfficeResource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono zasobu o ID: " + request.getResourceId()));

        List<Reservation> existingReservations = reservationRepository.findByResource(resource);

        List<Reservation> conflicts = existingReservations.stream()
                .filter(res -> isOverlapping(res.getStartTime(), res.getEndTime(),
                        request.getStartTime(), request.getEndTime()))
                .toList();

        if (!conflicts.isEmpty()) {
            if (requestingUser.getRole() == User.Role.ROLE_MANAGER) {
                reservationRepository.deleteAll(conflicts);
            } else {
                throw new ResourceConflictException("Ten zasób jest już zarezerwowany w wybranym terminie.");
            }
        }

        Reservation newReservation = Reservation.builder()
                .user(requestingUser)
                .resource(resource)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        return reservationRepository.save(newReservation);
    }

    private boolean isOverlapping(java.time.LocalDateTime start1, java.time.LocalDateTime end1,
                                  java.time.LocalDateTime start2, java.time.LocalDateTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}
