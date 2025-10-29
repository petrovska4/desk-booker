package org.example.core.repository;

import org.example.core.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public interface ReservationRepository extends GenericRepository<Reservation> {
    List<Reservation> findByDeskUuidAndStartDateAfterAndEndDateBefore(UUID deskUuid, LocalDate startDate, LocalDate endDate);
    Boolean existsByDeskUuidAndStartDateAfterAndEndDateBefore(UUID deskUuid, LocalDate startDate, LocalDate endDate);
    Boolean existsByEmployeeUuidAndStartDateBeforeAndEndDateAfter(UUID employeeUuid, LocalDate endDate, LocalDate startDate);

    List<Reservation> findByEmployeeUuidAndStartDateAfter(UUID employeeUuid, LocalDateTime startDate);
    List<Reservation> findByEmployeeUuidAndStartDateLessThanEqualAndEndDateGreaterThanEqual(UUID employeeUuid, LocalDate startDate, LocalDate endDate);

    @Query("""
    SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
    FROM Reservation r
    WHERE r.desk.uuid = :deskId
      AND r.startDate < :to
      AND r.endDate   > :from
  """)
    boolean existsOverlap(@Param("deskId") UUID deskId,
                          @Param("from") LocalDateTime from,
                          @Param("to") LocalDateTime to);

    @Query("""
    SELECT r.desk.uuid
    FROM Reservation r
    WHERE r.startDate < :to
      AND r.endDate   > :from
    GROUP BY r.desk.uuid
  """)
    List<UUID> busyDeskIds(@Param("from") LocalDateTime from,
                           @Param("to") LocalDateTime to);

    @Query("""
    SELECT r
    FROM Reservation r
    WHERE r.desk.uuid = :deskId AND r.startDate >= :now
    ORDER BY r.startDate ASC
  """)
    List<Reservation> findNext(@Param("deskId") UUID deskId,
                               @Param("now") LocalDateTime now);

    @Query("""
    SELECT r
    FROM Reservation r
    WHERE r.desk.uuid IN :deskIds 
      AND r.startDate >= :now
    ORDER BY r.desk.uuid, r.startDate ASC
  """)
    List<Reservation> nextReservations(@Param("deskIds") List<UUID> deskIds,
                                       @Param("now") LocalDateTime now);
}
