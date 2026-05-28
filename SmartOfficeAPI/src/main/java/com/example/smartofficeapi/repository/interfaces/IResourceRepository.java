package com.example.smartofficeapi.repository.interfaces;

import com.example.smartofficeapi.entities.OfficeResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResourceRepository extends JpaRepository<OfficeResource, Long> {
}
