package org.example.core.repository;

import org.example.core.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface ReservationRepository extends GenericRepository<Reservation> {
    List<Reservation> findByDeskUuidAndStartDateAfterAndEndDateBefore(UUID deskUuid, LocalDate startDate, LocalDate endDate);
    Boolean existsByDeskUuidAndStartDateAfterAndEndDateBefore(UUID deskUuid, LocalDate startDate, LocalDate endDate);
    Boolean existsByEmployeeUuidAndStartDateBeforeAndEndDateAfter(UUID employeeUuid, LocalDate endDate, LocalDate startDate);

    List<Reservation> findByEmployeeUuidAndStartDateAfter(UUID employeeUuid, LocalDate startDate);
    List<Reservation> findByEmployeeUuidAndStartDateLessThanEqualAndEndDateGreaterThanEqual(UUID employeeUuid, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT r.* FROM reservations r " +
            "JOIN daily_status ds ON r.uuid = ds.reservation_id " +
            "WHERE ds.status = 'AVAILABLE' " +
            "AND ds.date BETWEEN :startDate AND :endDate " +
            "AND r.start_date <= :endDate " +
            "AND r.end_date >= :startDate" +
            "AND r.deskId = :deskId",
            nativeQuery = true)
    List<Reservation> findAvailableReservationsBetweenDates(@Param("deskId") UUID deskId,
                                                            @Param("startDate") LocalDate startDate,
                                                            @Param("endDate") LocalDate endDate);
}
