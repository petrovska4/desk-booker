package org.example.restapi.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.OfficeTypeEnum;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfficeUpdateDto implements GenericUpdateDto {
    private String id;
    private String name;
    private OfficeTypeEnum type;
    private String locationId;
}
