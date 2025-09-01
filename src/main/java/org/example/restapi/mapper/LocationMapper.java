package org.example.restapi.mapper;

import org.example.core.model.Location;
import org.example.restapi.dto.LocationDto;
import org.example.restapi.dto.create.LocationCreateDto;
import org.example.restapi.dto.update.LocationUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CommonMapper.class})
public interface LocationMapper {

    @Named("toLocationDto")
    @Mapping(source = "uuid", target = "id", qualifiedByName = "mapUuidToId")
    LocationDto toLocationDto(Location location);

    @Mapping(source = "id", target = "uuid", qualifiedByName = "mapIdToUuid")
    Location toLocation(LocationDto locationDto);

    Location toLocation(LocationCreateDto locationCreateDto);

    LocationCreateDto toLocationCreateDto(LocationDto locationDto);

    @IterableMapping(qualifiedByName = "toLocationDto")
    List<LocationDto> toLocationDtos(List<Location> location);

    void updateLocationFromDto(LocationUpdateDto locationUpdateDto, @MappingTarget Location location);
}
