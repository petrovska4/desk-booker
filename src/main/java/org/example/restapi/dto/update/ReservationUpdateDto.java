package org.example.restapi.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationUpdateDto implements GenericUpdateDto {
    private String id;
    private Duration usage;
    private LocalDate startDate;
    private LocalDate endDate;
    private DeskUpdateDto desk;
    private EmployeeUpdateDto employee;
    private String deskId;
    private String employeeId;
}
