package com.example.smartofficeapi.repository.interfaces;

import com.example.smartofficeapi.entities.OfficeResource;
import com.example.smartofficeapi.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByResource(OfficeResource resource);
}