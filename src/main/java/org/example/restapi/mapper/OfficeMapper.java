package org.example.restapi.mapper;

import org.example.core.model.Location;
import org.example.core.model.Office;
import org.example.restapi.dto.OfficeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {CommonMapper.class})
public interface OfficeMapper {
//    OfficeMapper INSTANCE = Mappers.getMapper(OfficeMapper.class);

//    @Named("toOffice")
//    @Mapping(source = "id", target = "uuid", qualifiedByName = "mapIdToUuid")
//    @Mapping(source = "location", target = "location", qualifiedByName = "toLocation")
//    Office toOffice(string officeDto);

    @Named("toOfficeFromId")
    default Office toOfficeFromId(String officeId) {
        if (officeId == null) return null;
        Office office = new Office();
        UUID genUuid = UUID.fromString(officeId);
        office.setUuid(genUuid);
        return office;
    }

//    @Mapping(source = "location.uuid", target = "locationId")
    OfficeDto toOfficeDto(Office office);

//    @Mapping(source = "locationUuid", target = "location")
    Office toOffice(OfficeDto dto);

    default Location map(UUID locationUuid) {
        if (locationUuid == null) return null;
        Location location = new Location();
        location.setUuid(locationUuid);
        return location;
    }

    default UUID map(Location location) {
        return location != null ? location.getUuid() : null;
    }
}
