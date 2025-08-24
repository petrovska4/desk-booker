package org.example.restapi.mapper;

import org.example.core.model.Desk;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.create.DeskCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CommonMapper.class, OfficeMapper.class})
public interface DeskMapper {
//    DeskMapper INSTANCE = Mappers.getMapper(DeskMapper.class);

//    @Named("toDeskDto")
    @Mapping(source = "uuid", target = "id", qualifiedByName = "mapUuidToId")
//    @Mapping(source = "office", target = "office", qualifiedByName = "toOfficeDto")
    DeskDto toDeskDto(Desk desk);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(source = "officeId", target = "office", qualifiedByName = "toOfficeFromId")
    Desk toDesk(DeskCreateDto deskCreateDto);

}
