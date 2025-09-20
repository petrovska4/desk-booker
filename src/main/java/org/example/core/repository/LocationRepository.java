package org.example.core.repository;

import org.example.core.model.Location;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationRepository extends GenericRepository<Location>  {
    @EntityGraph(attributePaths = {"offices", "offices.desks"})
    @Query("SELECT l FROM Location l")
    List<Location> findAllWithOfficesAndDesks();
}
