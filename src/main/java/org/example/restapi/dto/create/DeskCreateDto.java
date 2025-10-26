package org.example.restapi.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.DeskStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeskCreateDto {
    private String label;
    private List<String> features;
    private DeskStatus status = DeskStatus.UNAVAILABLE;
    private String officeId;
}