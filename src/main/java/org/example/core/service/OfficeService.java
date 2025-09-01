package org.example.core.service;

import org.example.core.model.Office;
import org.example.core.repository.OfficeRepository;
import org.example.restapi.dto.OfficeDto;
import org.example.restapi.dto.create.OfficeCreateDto;
import org.example.restapi.dto.update.OfficeUpdateDto;
import org.example.restapi.mapper.OfficeMapper;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OfficeService extends GenericService<Office, OfficeDto> {
    private OfficeMapper officeMapper;

    @Autowired
    public OfficeService(OfficeRepository officeRepository,  OfficeMapper officeMapper) {
        super(officeRepository);
        this.officeMapper = officeMapper;
    }

    public OfficeDto getOfficeById(UUID uuid) {
        Office office = findById(uuid);
        return officeMapper.toOfficeDto(office);
    }

    public List<OfficeDto> getAllOffices() {
        return officeMapper.toOfficeDtos(findAll());
    }

    public OfficeDto createOffice(OfficeCreateDto officeCreateDto) {
        Preconditions.checkArgument(officeCreateDto.getLocationId() != null, "You need to send location id");

        Office office = officeMapper.toOffice(officeCreateDto);

        Office created = create(office);

        return officeMapper.toOfficeDto(created);
    }
}
