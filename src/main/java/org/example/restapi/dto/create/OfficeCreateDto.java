package org.example.restapi.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.OfficeTypeEnum;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfficeCreateDto {
    private String name;
    private OfficeTypeEnum type;
    private String locationId;
}
