package org.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.LocationStatusEnum;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationDto implements GenericDto {
    private String id;
    private String name;
    private String address;
    private LocationStatusEnum status;
}
