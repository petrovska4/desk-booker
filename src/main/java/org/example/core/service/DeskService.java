package org.example.core.service;

import org.example.core.model.Desk;
import org.example.core.model.Office;
import org.example.core.repository.DeskRepository;
import org.example.core.repository.OfficeRepository;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.create.DeskCreateDto;
import org.example.restapi.dto.update.DeskUpdateDto;
import org.example.restapi.mapper.DeskMapper;
import org.example.restapi.mapper.OfficeMapper;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeskService extends GenericService<Desk, DeskDto, DeskUpdateDto> {
    private final DeskMapper deskMapper;
    DeskRepository deskRepository;
    OfficeRepository officeRepository;

    @Autowired
    public DeskService(DeskRepository deskRepository, OfficeRepository officeRepository, DeskMapper deskMapper) {
        super(deskRepository);
        this.deskRepository = deskRepository;
        this.officeRepository = officeRepository;
        this.deskMapper = deskMapper;
    }

    public DeskDto getDeskById(UUID uuid) {
        return deskMapper.toDeskDto(findById(uuid));
    }

    public DeskDto createDesk(DeskCreateDto deskCreateDto) {
        Preconditions.checkArgument(deskCreateDto.getOfficeId() != null, "You need to send office id");

        UUID officeUuid = UUID.fromString(deskCreateDto.getOfficeId());
        Office office = officeRepository.findById(officeUuid)
                .orElseThrow(() -> new IllegalArgumentException("Office not found"));

        Desk desk = deskMapper.toDesk(deskCreateDto);
        desk.setOffice(office);
        Desk created = create(desk);

        return deskMapper.toDeskDto(created);
    }
}
