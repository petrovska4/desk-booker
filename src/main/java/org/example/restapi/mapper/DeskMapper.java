package org.example.restapi.mapper;

import org.example.core.model.Desk;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.create.DeskCreateDto;
import org.example.restapi.dto.update.DeskUpdateDto;
import org.mapstruct.*;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {OfficeMapper.class, CommonMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeskMapper {

    @Mapping(source = "uuid", target = "id")
    @Mapping(target = "next", expression = "java(getNextReservation(desk))")
    @Mapping(target = "office", ignore = true)
    DeskDto toDeskDto(Desk desk);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(source = "officeId", target = "office", qualifiedByName = "toOfficeFromId")
    Desk toDesk(DeskCreateDto deskCreateDto);

    @Mapping(source = "officeId", target = "office", qualifiedByName = "toOfficeFromId")
    Desk toDesk(DeskUpdateDto deskUpdateDto);

    List<DeskDto> toDeskDtos(List<Desk> desks);

    @Named("toDeskDtoShallow")
    @Mapping(source = "uuid", target = "id", qualifiedByName = "mapUuidToId")
    @Mapping(target = "office", ignore = true)
    DeskDto toDeskDtoShallow(Desk desk);

    void updateDeskFromDto(DeskUpdateDto dto, @MappingTarget Desk entity);

    default String getNextReservation(Desk desk) {
        return Optional.ofNullable(desk.getReservations())
                .flatMap(res -> res.stream()
                        .filter(r -> r.getStartDate().isAfter(java.time.LocalDateTime.now()))
                        .min(Comparator.comparing(r -> r.getStartDate()))
                )
                .map(r -> r.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .orElse(null);
    }
}

