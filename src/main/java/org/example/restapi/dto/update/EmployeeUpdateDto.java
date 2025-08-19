package org.example.restapi.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.EmployeePositionEnum;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeUpdateDto implements GenericUpdateDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private EmployeePositionEnum position;
}
