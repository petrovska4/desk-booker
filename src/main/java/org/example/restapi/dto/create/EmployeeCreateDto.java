package org.example.restapi.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.EmployeePositionEnum;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeCreateDto {
    private String firstName;
    private String lastName;
    private String email;
    private EmployeePositionEnum position;
}
