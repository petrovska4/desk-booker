package org.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.OfficeTypeEnum;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfficeDto implements GenericDto {
    private String id;
    private String name;
    private OfficeTypeEnum type;
    private LocationDto location;
}
