package com.example.smartofficeapi.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest {
    private Long resourceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}