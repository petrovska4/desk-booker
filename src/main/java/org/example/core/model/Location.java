package org.example.core.model;

import lombok.*;
import org.example.core.model.enumeration.LocationStatusEnum;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"offices"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Table(name = "locations")
public class Location implements GenericEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID uuid;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private LocationStatusEnum status;
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Office> offices;
}
