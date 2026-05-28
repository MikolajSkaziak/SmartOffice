package com.example.smartofficeapi.services;

import com.example.smartofficeapi.dto.ReservationRequest;
import com.example.smartofficeapi.entities.Desk;
import com.example.smartofficeapi.entities.OfficeResource;
import com.example.smartofficeapi.entities.Reservation;
import com.example.smartofficeapi.entities.User;
import com.example.smartofficeapi.exceptions.ResourceConflictException;
import com.example.smartofficeapi.repository.interfaces.IReservationRepository;
import com.example.smartofficeapi.repository.interfaces.IResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private IReservationRepository reservationRepository;

    @Mock
    private IResourceRepository resourceRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReservation_Success() {
        User user = User.builder().role(User.Role.ROLE_EMPLOYEE).build();
        OfficeResource desk = new Desk();
        desk.setId(1L);

        ReservationRequest request = new ReservationRequest();
        request.setResourceId(1L);
        request.setStartTime(LocalDateTime.now().plusDays(1));
        request.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));

        when(resourceRepository.findById(1L)).thenReturn(Optional.of(desk));
        when(reservationRepository.findByResource(desk)).thenReturn(List.of());
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

        Reservation result = reservationService.createReservation(request, user);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(desk, result.getResource());
    }

    @Test
    void createReservation_ConflictThrowsException() {
        User employee = User.builder().role(User.Role.ROLE_EMPLOYEE).build();
        OfficeResource desk = new Desk();
        desk.setId(1L);

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);

        Reservation existing = Reservation.builder()
                .startTime(start.minusHours(1))
                .endTime(start.plusHours(1))
                .build();

        ReservationRequest request = new ReservationRequest();
        request.setResourceId(1L);
        request.setStartTime(start);
        request.setEndTime(end);

        when(resourceRepository.findById(1L)).thenReturn(Optional.of(desk));
        when(reservationRepository.findByResource(desk)).thenReturn(List.of(existing));

        assertThrows(ResourceConflictException.class, () -> reservationService.createReservation(request, employee));
    }
}
