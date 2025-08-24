package org.example.core.service;

import org.example.core.model.Office;
import org.example.core.repository.OfficeRepository;
import org.example.restapi.dto.OfficeDto;
import org.example.restapi.dto.update.OfficeUpdateDto;
import org.example.restapi.mapper.OfficeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OfficeService extends GenericService<Office, OfficeDto, OfficeUpdateDto> {
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
}
