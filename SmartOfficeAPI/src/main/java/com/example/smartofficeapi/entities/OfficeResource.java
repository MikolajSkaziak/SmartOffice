package com.example.smartofficeapi.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resources")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "resource_type", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class OfficeResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}