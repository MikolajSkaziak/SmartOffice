package com.example.smartofficeapi.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("DESK")
@Getter @Setter
public class Desk extends OfficeResource {
    private boolean hasMonitors;
}