package org.example.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.DateStatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "reservations")
public class Reservation implements GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "desk_uuid",nullable = false)
    private Desk desk;

    @ManyToOne
    @JoinColumn(name = "employee_uuid",nullable = false)
    private Employee employee;
}
