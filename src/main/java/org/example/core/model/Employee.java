package org.example.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.EmployeePositionEnum;
import org.example.core.model.enumeration.RoleEnum;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "employees")
public class Employee implements GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    @Column(nullable = false)
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private EmployeePositionEnum position;
    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.EMPLOYEE;

}
