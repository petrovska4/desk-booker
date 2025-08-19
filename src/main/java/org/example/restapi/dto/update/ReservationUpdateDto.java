package org.example.restapi.dto.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationUpdateDto implements GenericUpdateDto {
    private String id;
//    @NonUpdatableField
    private Duration usage;
    private LocalDate startDate;
    private LocalDate endDate;
    private DeskUpdateDto desk;
    private EmployeeUpdateDto employee;
    private String deskId;
    private String employeeId;
}
