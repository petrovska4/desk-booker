package org.example.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.OfficeTypeEnum;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "offices")
public class Office implements GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID uuid;
    private String name;
    @Enumerated(EnumType.STRING)
    private OfficeTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "location_uuid", nullable = false)
    private Location location;
}