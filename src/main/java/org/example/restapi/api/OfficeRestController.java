package org.example.restapi.api;

import org.example.core.service.OfficeService;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.OfficeDto;
import org.example.restapi.dto.create.DeskCreateDto;
import org.example.restapi.dto.create.OfficeCreateDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/office")
public class OfficeRestController {
    private final OfficeService officeService;

    public OfficeRestController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping("/create")
    public OfficeDto create(@RequestBody OfficeCreateDto officeCreateDto) {
        return officeService.createOffice(officeCreateDto);
    }
}
