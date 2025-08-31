package org.example.restapi.mapper;

import org.example.core.model.Location;
import org.example.core.model.Office;
import org.example.restapi.dto.OfficeDto;
import org.example.restapi.dto.create.OfficeCreateDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {CommonMapper.class, LocationMapper.class})
public interface OfficeMapper {

    @Named("toOfficeFromId")
    default Office toOfficeFromId(String officeId) {
        if (officeId == null) return null;
        Office office = new Office();
        UUID genUuid = UUID.fromString(officeId);
        office.setUuid(genUuid);
        return office;
    }

    @Named("toOfficeDto")
    @Mapping(source = "location", target = "location", qualifiedByName = "toLocationDto")
    @Mapping(source = "uuid", target = "id", qualifiedByName = "mapUuidToId")
    OfficeDto toOfficeDto(Office office);

    @Mapping(source = "id", target = "uuid", qualifiedByName = "mapIdToUuid")
    Office toOffice(OfficeDto dto);

    @Mapping(source = "locationId", target = "location")
    Office toOffice(OfficeCreateDto dto);

    default Location map(UUID locationUuid) {
        if (locationUuid == null) return null;
        Location location = new Location();
        location.setUuid(locationUuid);
        return location;
    }

    default UUID map(Location location) {
        return location != null ? location.getUuid() : null;
    }

    @IterableMapping(qualifiedByName = "toOfficeDto")
    List<OfficeDto> toOfficeDtos(List<Office> offices);
}
