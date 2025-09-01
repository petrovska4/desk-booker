package org.example.restapi.mapper;

import org.example.core.model.Desk;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.create.DeskCreateDto;
import org.example.restapi.dto.update.DeskUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CommonMapper.class, OfficeMapper.class})
public interface DeskMapper {

    @Named("toDeskDto")
    @Mapping(source = "uuid", target = "id")
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

}
