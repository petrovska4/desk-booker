package org.example.core.service;

import io.micrometer.common.lang.Nullable;
import org.example.core.model.Desk;
import org.example.core.model.Location;
import org.example.core.model.Office;
import org.example.core.model.Reservation;
import org.example.core.model.enumeration.DeskStatus;
import org.example.core.repository.LocationRepository;
import org.example.core.repository.ReservationRepository;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.LocationDto;
import org.example.restapi.dto.create.LocationCreateDto;
import org.example.restapi.dto.update.LocationUpdateDto;
import org.example.restapi.mapper.DeskMapper;
import org.example.restapi.mapper.LocationMapper;
import org.example.restapi.mapper.OfficeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LocationService extends GenericService<Location, LocationDto> {
    LocationRepository locationRepository;
    LocationMapper locationMapper;
    OfficeService officeService;
    OfficeMapper officeMapper;
    DeskMapper deskMapper;
    ReservationRepository reservationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository,  LocationMapper locationMapper, OfficeService officeService,  OfficeMapper officeMapper,  DeskMapper deskMapper,   ReservationRepository reservationRepository) {
        super(locationRepository);
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.officeService = officeService;
        this.officeMapper = officeMapper;
        this.deskMapper = deskMapper;
        this.reservationRepository = reservationRepository;
    }

    public LocationDto getLocationById(UUID uuid) {
        return locationMapper.toLocationDto(findById(uuid));
    }

    public List<LocationDto> getAllLocations() {
        return locationMapper.toLocationDtos(findAll());
    }

    public List<LocationDto> getLocationsWithOfficesAndDesks(@Nullable LocalDateTime from, @Nullable LocalDateTime to) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1).minusNanos(1);

        var locations = locationRepository.findAll();
        var offices = officeService.getAllOfficesWithDesks();

        Map<UUID, Set<Office>> officesByLocation = offices.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getLocation().getUuid(),
                        Collectors.toCollection(LinkedHashSet::new)
                ));

        List<UUID> allDeskIds = offices.stream()
                .flatMap(o -> o.getDesks().stream())
                .map(Desk::getUuid)
                .toList();

        Set<UUID> busyToday = new HashSet<>(reservationRepository.busyDeskIds(startOfToday, endOfToday));

        Map<UUID, Reservation> nextByDesk = reservationRepository.nextReservations(allDeskIds, now)
                .stream()
                .collect(Collectors.toMap(
                        r -> r.getDesk().getUuid(),
                        r -> r,
                        (a, b) -> a
                ));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return locations.stream().map(location -> {
            var locationDto = locationMapper.toLocationDtoShallow(location);

            var officesForLocation = officesByLocation
                    .getOrDefault(location.getUuid(), Set.of())
                    .stream()
                    .map(office -> officeMapper.toOfficeDtoWithDesks(office, desk -> {
                        DeskDto dto = deskMapper.toDeskDtoShallow(desk);

                        if (desk.getStatus() == DeskStatus.UNAVAILABLE) {
                            dto.setStatus(DeskStatus.UNAVAILABLE);
                            dto.setNext("Unavailable for maintenance");
                            return dto;
                        }

                        if (busyToday.contains(desk.getUuid())) {
                            dto.setStatus(DeskStatus.RESERVED);
                            dto.setNext("Currently reserved");
                            return dto;
                        }

                        dto.setStatus(DeskStatus.AVAILABLE);
                        var next = nextByDesk.get(desk.getUuid());

                        if (next != null) {
                            String employeeName = next.getEmployee() != null
                                    ? next.getEmployee().getFirstName() + " " + next.getEmployee().getLastName()
                                    : "Unknown";

                            dto.setNext("Next: %s–%s by %s".formatted(
                                    next.getStartDate().format(formatter),
                                    next.getEndDate().format(formatter),
                                    employeeName
                            ));
                        } else {
                            dto.setNext("Free all day");
                        }

                        return dto;
                    }))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

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
