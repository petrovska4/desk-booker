package org.example.restapi.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationCreateDto {
//    @NonUpdatableField
    private Duration usage;
    private LocalDate startDate;
    private LocalDate endDate;
    private DeskCreateDto desk;
    private EmployeeCreateDto employee;
    private String deskId;
    private String employeeId;
}
