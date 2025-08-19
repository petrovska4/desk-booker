package org.example.restapi.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeskUpdateDto implements GenericUpdateDto {
    private String id;
    private int position;
    private String officeId;
}