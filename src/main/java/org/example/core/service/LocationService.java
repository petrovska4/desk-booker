package org.example.core.service;

import org.example.core.model.Location;
import org.example.core.model.Office;
import org.example.core.repository.LocationRepository;
import org.example.restapi.dto.LocationDto;
import org.example.restapi.dto.create.LocationCreateDto;
import org.example.restapi.dto.update.LocationUpdateDto;
import org.example.restapi.mapper.DeskMapper;
import org.example.restapi.mapper.LocationMapper;
import org.example.restapi.mapper.OfficeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LocationService extends GenericService<Location, LocationDto> {
    LocationRepository locationRepository;
    LocationMapper locationMapper;
    OfficeService officeService;
    OfficeMapper officeMapper;
    DeskMapper deskMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository,  LocationMapper locationMapper, OfficeService officeService,  OfficeMapper officeMapper,  DeskMapper deskMapper) {
        super(locationRepository);
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.officeService = officeService;
        this.officeMapper = officeMapper;
        this.deskMapper = deskMapper;
    }

    public LocationDto getLocationById(UUID uuid) {
        return locationMapper.toLocationDto(findById(uuid));
    }

    public List<LocationDto> getAllLocations() {
        return locationMapper.toLocationDtos(findAll());
    }

    public List<LocationDto> getLocationsWithOfficesAndDesks() {
        var locations = locationRepository.findAll(); // all locations
        var offices = officeService.getAllOfficesWithDesks(); // offices already have desks fetched

        return locations.stream().map(location -> {
            var locationDto = locationMapper.toLocationDtoShallow(location);

            var officesForLocation = offices.stream()
                    .filter(o -> o.getLocation().getUuid().equals(location.getUuid()))
                    .map(office -> officeMapper.toOfficeDtoWithDesks(office, deskMapper))
                    .collect(Collectors.toSet());

            locationDto.setOffices(officesForLocation);

            return locationDto;
        }).toList();
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
