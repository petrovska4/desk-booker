package org.example.restapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.ElementCollection;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationDto implements GenericDto {
    private String id;
    private Duration usage;
    private LocalDate startDate;
    private LocalDate endDate;
    private DeskDto desk;
    private EmployeeDto employee;

    @JsonIgnore
    public Duration getDuration() {
        return Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay());
    }

    @ElementCollection
    private Set<LocalDate> occupancyDates;
}
