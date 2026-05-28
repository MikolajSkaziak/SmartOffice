package com.example.smartofficeapi.factory;

import com.example.smartofficeapi.dto.ResourceCreationRequest;
import com.example.smartofficeapi.entities.Desk;
import com.example.smartofficeapi.entities.MeetingRoom;
import com.example.smartofficeapi.entities.OfficeResource;

import org.springframework.stereotype.Component;

@Component
public class ResourceFactory {

    public OfficeResource createResource(ResourceCreationRequest request) {
        return switch (request.getType().toUpperCase()) {
            case "DESK" -> {
                Desk desk = new Desk();
                desk.setName(request.getName());
                desk.setHasMonitors(request.isHasMonitors());
                yield desk;
            }
            case "ROOM" -> {
                MeetingRoom room = new MeetingRoom();
                room.setName(request.getName());
                room.setCapacity(request.getCapacity());
                room.setHasProjector(request.isHasProjector());
                yield room;
            }
            default -> throw new IllegalArgumentException("Nieobsługiwany typ zasobu: " + request.getType());
        };
    }
}