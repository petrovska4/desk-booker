package org.example.restapi.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationCreateDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String deskId;
    private String employeeId;
}
