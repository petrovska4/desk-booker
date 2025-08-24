package org.example.core.service;

import org.example.core.model.Location;
import org.example.core.repository.LocationRepository;
import org.example.restapi.dto.LocationDto;
import org.example.restapi.dto.create.LocationCreateDto;
import org.example.restapi.dto.update.LocationUpdateDto;
import org.example.restapi.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService extends GenericService<Location, LocationDto, LocationUpdateDto> {
    LocationRepository locationRepository;
    LocationMapper locationMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository,  LocationMapper locationMapper) {
        super(locationRepository);
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public LocationDto createLocation(LocationCreateDto locationCreateDto) {
        return locationMapper.toLocationDto(create(locationMapper.toLocation(locationCreateDto)));
    }
}
