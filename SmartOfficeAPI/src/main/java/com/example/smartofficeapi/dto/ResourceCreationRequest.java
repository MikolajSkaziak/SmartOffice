package com.example.smartofficeapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceCreationRequest {
    private String type;
    private String name;

    private boolean hasMonitors;

    private int capacity;
    private boolean hasProjector;
}