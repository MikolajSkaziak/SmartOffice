package com.example.smartofficeapi.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("ROOM")
@Getter @Setter
public class MeetingRoom extends OfficeResource {
    private int capacity;
    private boolean hasProjector;
}
