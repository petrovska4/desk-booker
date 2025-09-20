package org.example.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.example.core.model.enumeration.DeskStatus;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "desks")
public class Desk implements GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID uuid;
    private String label;

    @ManyToOne
    @JoinColumn(name = "office_uuid", nullable = false)
    private Office office;

    @Enumerated(EnumType.STRING)
    private DeskStatus status;

    private String features;

    @OneToMany(mappedBy = "desk", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}
