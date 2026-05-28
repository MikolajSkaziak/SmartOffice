package com.example.smartofficeapi.services;

import com.example.smartofficeapi.dto.ResourceCreationRequest;
import com.example.smartofficeapi.entities.OfficeResource;
import com.example.smartofficeapi.factory.ResourceFactory;
import com.example.smartofficeapi.repository.interfaces.IResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final IResourceRepository resourceRepository;
    private final ResourceFactory resourceFactory;

    public OfficeResource createResource(ResourceCreationRequest request) {
        OfficeResource newResource = resourceFactory.createResource(request);

        return resourceRepository.save(newResource);
    }

    public List<OfficeResource> getAllResources() {
        return resourceRepository.findAll();
    }
}