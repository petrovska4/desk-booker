package org.example.restapi.api;

import org.example.core.service.LocationService;
import org.example.restapi.dto.LocationDto;
import org.example.restapi.dto.create.LocationCreateDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/location")
public class LocationRestController {
    private final LocationService locationService;

    public LocationRestController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/get-by-id")
    public LocationDto getById(@RequestParam String id) {
        return locationService.getLocationById(UUID.fromString(id));
    }

    @GetMapping("/get-all")
    public List<LocationDto> findAll() {
        return locationService.getAllLocations();
    }

    @PostMapping("/create")
    public LocationDto createLocation(@RequestBody LocationCreateDto locationCreateDto) {
        return locationService.createLocation(locationCreateDto);
    }


}
