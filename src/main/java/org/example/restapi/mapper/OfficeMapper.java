package org.example.restapi.mapper;

import org.example.core.model.Desk;
import org.example.core.model.Location;
import org.example.core.model.Office;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.OfficeDto;
import org.example.restapi.dto.create.OfficeCreateDto;
import org.example.restapi.dto.update.OfficeUpdateDto;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CommonMapper.class, LocationMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OfficeMapper {

    @Named("toOfficeFromId")
    default Office toOfficeFromId(String officeId) {
        if (officeId == null) return null;
        Office office = new Office();
        office.setUuid(UUID.fromString(officeId));
        return office;
    }

    @Named("toOfficeDto")
    @Mapping(source = "uuid", target = "id", qualifiedByName = "mapUuidToId")
    @Mapping(target = "location", ignore = true)
    OfficeDto toOfficeDto(Office office);

    @Mapping(source = "id", target = "uuid", qualifiedByName = "mapIdToUuid")
    Office toOffice(OfficeDto dto);

    @Mapping(source = "locationId", target = "location")
    Office toOffice(OfficeCreateDto dto);

    @Named("toOfficeDtoWithDesks")
    @Mapping(source = "uuid", target = "id")
    @Mapping(target = "location", ignore = true)
    OfficeDto toOfficeDtoWithDesks(Office office);

    @IterableMapping(qualifiedByName = "toOfficeDtoWithDesks")
    Set<OfficeDto> toOfficeDtosWithDesks(Set<Office> offices);

    @Named("toOfficeDtoShallow")
    @Mapping(source = "uuid", target = "id")
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "desks", ignore = true)
    OfficeDto toOfficeDtoShallow(Office office);

    default OfficeDto toOfficeDtoWithDesks(Office office, Function<Desk, DeskDto> deskMapperFn) {
        OfficeDto dto = toOfficeDtoShallow(office);
        if (office.getDesks() != null) {
            dto.setDesks(
                    office.getDesks().stream()
                            .map(deskMapperFn)
                            .collect(Collectors.toSet())
            );
        }
        return dto;
    }

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

    void updateOfficeFromDto(OfficeUpdateDto officeUpdateDto, @MappingTarget Office office);
}
