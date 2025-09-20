package org.example.core.repository;

import org.example.core.model.Office;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OfficeRepository extends GenericRepository<Office> {
    @Query("SELECT o FROM Office o LEFT JOIN FETCH o.desks")
    List<Office> findAllWithDesks();
}
