package org.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.EmployeePositionEnum;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDto implements GenericDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private EmployeePositionEnum position;
}
