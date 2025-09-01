package org.example.core.service;

import org.example.core.model.Location;
import org.example.core.repository.LocationRepository;
import org.example.restapi.dto.LocationDto;
import org.example.restapi.dto.create.LocationCreateDto;
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
}
