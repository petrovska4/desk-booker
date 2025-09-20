package org.example.core.model;

import lombok.*;
import org.example.core.model.enumeration.OfficeTypeEnum;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"desks", "location"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Desk> desks;
}