package org.example.restapi.api;

import org.example.core.service.DeskService;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.create.DeskCreateDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/desk")
public class DeskRestController {
    private final DeskService deskService;

    public DeskRestController(DeskService deskService) {
        this.deskService = deskService;
    }

    @GetMapping("/get-by-id")
    public DeskDto getById(@RequestParam String id) {
        return deskService.getDeskById(UUID.fromString(id));
    }

    @GetMapping("/get-all")
    public List<DeskDto> getAll() {
        return deskService.getAllDesks();
    }

    @PostMapping("/create")
    public DeskDto create(@RequestBody DeskCreateDto deskDto) {
        return deskService.createDesk(deskDto);
    }
}
