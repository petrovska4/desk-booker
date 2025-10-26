package org.example.restapi.api;

import org.example.core.service.DeskService;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.create.DeskCreateDto;
import org.example.restapi.dto.update.DeskUpdateDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/desk")
@CrossOrigin(origins = "http://localhost:5173")
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

    @PostMapping
    public DeskDto create(@RequestBody DeskCreateDto deskDto) {
        return deskService.createDesk(deskDto);
    }

    @PutMapping("/update")
    public DeskDto update(@RequestParam String id, @RequestBody DeskUpdateDto updates) {
        return deskService.updateDesk(UUID.fromString(id), updates);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String id) {
        deskService.deleteDesk(UUID.fromString(id));
    }
}
