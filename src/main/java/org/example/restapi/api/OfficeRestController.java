package org.example.restapi.api;

import org.example.core.service.OfficeService;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.OfficeDto;
import org.example.restapi.dto.create.OfficeCreateDto;
import org.example.restapi.dto.update.DeskUpdateDto;
import org.example.restapi.dto.update.OfficeUpdateDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/office")
public class OfficeRestController {
    private final OfficeService officeService;

    public OfficeRestController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping("/get-by-id")
    public OfficeDto getById(@RequestParam String id) {
        return officeService.getOfficeById(UUID.fromString(id));
    }

    @GetMapping("/get-all")
    public List<OfficeDto> getAll() {
        return officeService.getAllOffices();
    }

    @PostMapping("/create")
    public OfficeDto create(@RequestBody OfficeCreateDto officeCreateDto) {
        return officeService.createOffice(officeCreateDto);
    }

    @PutMapping("/update")
    public OfficeDto update(@RequestParam String id, @RequestBody OfficeUpdateDto officeUpdateDto) {
        return officeService.updateOffice(UUID.fromString(id), officeUpdateDto);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String id) {
        officeService.deleteOffice(UUID.fromString(id));
    }
}
