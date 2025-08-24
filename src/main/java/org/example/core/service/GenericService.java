package org.example.core.service;

import org.example.core.model.GenericEntity;
import org.example.core.repository.GenericRepository;
import org.example.restapi.dto.GenericDto;
import org.example.restapi.dto.update.GenericUpdateDto;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;

import java.util.List;
import java.util.UUID;

public class GenericService<T extends GenericEntity, D extends GenericDto, U extends GenericUpdateDto> {
    private final GenericRepository<T> repository;

    public GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public T findById(UUID uuid) {
        Preconditions.checkArgument(uuid != null, "You can't send null id");

        return repository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid entity id: " + uuid));
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T create(T entity) {
        return repository.save(entity);
    }
}
