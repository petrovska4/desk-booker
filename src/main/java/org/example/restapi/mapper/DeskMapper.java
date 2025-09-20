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

@Mapper(componentModel = "spring", uses = {CommonMapper.class, OfficeMapper.class})
public interface DeskMapper {

    @Named("toDeskDto")
    @Mapping(source = "uuid", target = "id")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "next", expression = "java(getNextReservation(desk))")
    @Mapping(target = "features", expression = "java(parseFeatures(desk.getFeatures()))")
    @Mapping(source = "office", target = "office", qualifiedByName = "toOfficeDto")
    DeskDto toDeskDto(Desk desk);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(source = "officeId", target = "office", qualifiedByName = "toOfficeFromId")
    Desk toDesk(DeskCreateDto deskCreateDto);

    @Mapping(source = "officeId", target = "office", qualifiedByName = "toOfficeFromId")
    Desk toDesk(DeskUpdateDto deskUpdateDto);

    @IterableMapping(qualifiedByName = "toDeskDto")
    List<DeskDto> toDeskDtos(List<Desk> desks);

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

    default java.util.List<String> parseFeatures(String features) {
        if (features == null || features.isBlank()) return java.util.List.of();
        return java.util.List.of(features.split(","));
    }
}
