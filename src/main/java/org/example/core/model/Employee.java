package org.example.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.EmployeePositionEnum;

import javax.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private EmployeePositionEnum position;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @Fetch(FetchMode.JOIN)
//    @JoinTable(
//            name = "employee_team",
//            joinColumns = @JoinColumn(name = "employee_uuid"),
//            inverseJoinColumns = @JoinColumn(name = "team_uuid")
//    )
//    private List<Team> teams = new ArrayList<>();
}
