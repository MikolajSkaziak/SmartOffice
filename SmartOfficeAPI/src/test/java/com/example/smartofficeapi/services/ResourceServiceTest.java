package com.example.smartofficeapi.services;

import com.example.smartofficeapi.dto.ResourceCreationRequest;
import com.example.smartofficeapi.entities.Desk;
import com.example.smartofficeapi.entities.OfficeResource;
import com.example.smartofficeapi.factory.ResourceFactory;
import com.example.smartofficeapi.repository.interfaces.IResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ResourceServiceTest {

    @Mock
    private IResourceRepository resourceRepository;

    @Mock
    private ResourceFactory resourceFactory;

    @InjectMocks
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createResource_ShouldUseFactoryAndSaveToRepository() {

        ResourceCreationRequest request = new ResourceCreationRequest();
        request.setType("DESK");
        request.setName("Mockowane Biurko");

        Desk mockedDesk = new Desk();
        mockedDesk.setName("Mockowane Biurko");

        when(resourceFactory.createResource(request)).thenReturn(mockedDesk);
        when(resourceRepository.save(any(OfficeResource.class))).thenReturn(mockedDesk);

        OfficeResource result = resourceService.createResource(request);

        assertNotNull(result);
        assertEquals("Mockowane Biurko", result.getName());
        verify(resourceFactory).createResource(request);
        verify(resourceRepository).save(mockedDesk);
    }

    @Test
    void getAllResources_ShouldReturnListFromRepository() {
        Desk desk = new Desk();
        when(resourceRepository.findAll()).thenReturn(List.of(desk));

        List<OfficeResource> result = resourceService.getAllResources();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(resourceRepository).findAll();
    }
}
