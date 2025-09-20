package org.example.restapi.mapper;

import org.example.core.model.Location;
import org.example.restapi.dto.LocationDto;
import org.example.restapi.dto.create.LocationCreateDto;
import org.example.restapi.dto.update.LocationUpdateDto;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CommonMapper.class})
public interface LocationMapper {

    @Named("toLocationDto")
    @Mapping(source = "uuid", target = "id", qualifiedByName = "mapUuidToId")
    @Mapping(target = "offices", ignore = true)
    LocationDto toLocationDto(Location location);

    @Mapping(source = "id", target = "uuid", qualifiedByName = "mapIdToUuid")
    Location toLocation(LocationDto locationDto);

    Location toLocation(LocationCreateDto locationCreateDto);

    LocationCreateDto toLocationCreateDto(LocationDto locationDto);

    @IterableMapping(qualifiedByName = "toLocationDto")
    @Mapping(target = "offices", ignore = true)
    List<LocationDto> toLocationDtos(List<Location> locations);

    @Named("toLocationDtoShallow")
    @Mapping(source = "uuid", target = "id", qualifiedByName = "mapUuidToId")
    @Mapping(target = "offices", ignore = true)
    LocationDto toLocationDtoShallow(Location location);

    @Named("toLocationDto")
    default LocationDto toLocationDto(Location location, OfficeMapper officeMapper) {
        LocationDto dto = toLocationDtoShallow(location);
        if (location.getOffices() != null) {
            dto.setOffices(location.getOffices().stream()
                    .map(officeMapper::toOfficeDtoShallow) // Shallow mapping
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    default LocationDto toLocationDtoWithOffices(Location location, OfficeMapper officeMapper) {
        LocationDto dto = toLocationDtoShallow(location);

        if (location.getOffices() != null) {
            dto.setOffices(
                    location.getOffices().stream()
                            .map(officeMapper::toOfficeDtoShallow) // shallow
                            .collect(Collectors.toSet())
            );
        }

        return dto;
    }

    void updateLocationFromDto(LocationUpdateDto locationUpdateDto, @MappingTarget Location location);

    default List<?> mapOffices(List<?> offices) {
        return offices == null ? List.of() : offices;
    }
}
