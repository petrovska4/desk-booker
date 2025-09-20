package org.example.core.service;

import org.example.core.model.Desk;
import org.example.core.model.Office;
import org.example.core.repository.DeskRepository;
import org.example.core.repository.OfficeRepository;
import org.example.restapi.dto.DeskDto;
import org.example.restapi.dto.create.DeskCreateDto;
import org.example.restapi.dto.update.DeskUpdateDto;
import org.example.restapi.mapper.DeskMapper;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeskService extends GenericService<Desk, DeskDto> {
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

    public List<DeskDto> getAllDesks() {
        List<Desk> desks = deskRepository.findAll();
        return desks.stream()
                .map(deskMapper::toDeskDto)
                .collect(Collectors.toList());
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

    public DeskDto updateDesk(UUID uuid, DeskUpdateDto deskUpdateDto) {
        Desk existingDesk = findById(uuid);
        Preconditions.checkArgument(existingDesk != null, "Desk does not exist");

        deskMapper.updateDeskFromDto(deskUpdateDto, existingDesk);

        update(existingDesk);

        return deskMapper.toDeskDto(existingDesk);
    }

    public void deleteDesk(UUID uuid) {
        delete(uuid);
    }
}
