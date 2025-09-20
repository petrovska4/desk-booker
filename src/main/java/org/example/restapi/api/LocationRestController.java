package org.example.restapi.api;

import org.example.core.service.LocationService;
import org.example.restapi.dto.LocationDto;
import org.example.restapi.dto.create.LocationCreateDto;
import org.example.restapi.dto.update.LocationUpdateDto;
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

    @GetMapping("/get-all-with-offices-and-desks")
    public List<LocationDto> findAllWithOfficesAndDesks() {
        return locationService.getLocationsWithOfficesAndDesks();
    }

    @PostMapping("/create")
    public LocationDto createLocation(@RequestBody LocationCreateDto locationCreateDto) {
        return locationService.createLocation(locationCreateDto);
    }

    @PutMapping("/update")
    public LocationDto update(@RequestParam String id, @RequestBody LocationUpdateDto locationUpdateDto) {
        return locationService.updateLocation(UUID.fromString(id), locationUpdateDto);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String id) {
        locationService.deleteLocation(UUID.fromString(id));
    }


}
