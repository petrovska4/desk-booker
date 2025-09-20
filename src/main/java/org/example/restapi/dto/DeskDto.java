package org.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.DeskStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeskDto implements GenericDto {
    private String id;
    private String label;
    private DeskStatus status;
    private String next;
    private List<String> features;
    private OfficeDto office;
}