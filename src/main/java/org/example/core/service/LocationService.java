package org.example.core.service;

import org.example.core.model.Location;
import org.example.core.repository.LocationRepository;
import org.example.restapi.dto.LocationDto;
import org.example.restapi.dto.create.LocationCreateDto;
import org.example.restapi.dto.update.LocationUpdateDto;
import org.example.restapi.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LocationService extends GenericService<Location, LocationDto> {
    LocationRepository locationRepository;
    LocationMapper locationMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository,  LocationMapper locationMapper) {
        super(locationRepository);
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public LocationDto getLocationById(UUID uuid) {
        return locationMapper.toLocationDto(findById(uuid));
    }

    public List<LocationDto> getAllLocations() {
        return locationMapper.toLocationDtos(findAll());
    }

    public LocationDto createLocation(LocationCreateDto locationCreateDto) {
        return locationMapper.toLocationDto(create(locationMapper.toLocation(locationCreateDto)));
    }

    public LocationDto updateLocation(UUID uuid, LocationUpdateDto  locationUpdateDto) {
        Location existingEntity = locationRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid entity id: "));

        locationMapper.updateLocationFromDto(locationUpdateDto, existingEntity);

        update(existingEntity);

        return locationMapper.toLocationDto(existingEntity);
    }

    public void deleteLocation(UUID uuid) {
        delete(uuid);
    }
}
