package org.example.restapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationDto implements GenericDto {
    private String id;
    private Duration usage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private DeskDto desk;
    private EmployeeDto employee;
}
