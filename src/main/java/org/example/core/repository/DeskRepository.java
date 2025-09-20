package org.example.core.repository;

import org.example.core.model.Desk;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface DeskRepository extends GenericRepository<Desk> {
}
