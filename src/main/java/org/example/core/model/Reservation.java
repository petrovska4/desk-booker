package org.example.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.core.model.enumeration.DateStatusEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "reservations")
public class Reservation implements GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "desk_uuid",nullable = false)
    private Desk desk;

    @ManyToOne
    @JoinColumn(name = "employee_uuid",nullable = false)
    private Employee employee;

    @CollectionTable(name = "reservation_occupancy_dates", joinColumns = @JoinColumn(name = "reservation_uuid"))
    @ElementCollection
    @MapKeyColumn(name = "occupancy_date")
    private Set<LocalDate> occupancyDates = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "daily_status", joinColumns = @JoinColumn(name = "reservation_id"))
    @MapKeyColumn(name = "date")
    @Column(name = "status")
    private Map<LocalDate, DateStatusEnum> dailyStatus = new HashMap<>();

    public void addOccupancyDate(LocalDate occupancyDate) {
        this.occupancyDates.add(occupancyDate);
    }

    public void addDateAvailable (LocalDate date) {
        this.dailyStatus.put(date, DateStatusEnum.AVAILABLE);
    }

    public void addDateUnavailable (LocalDate date) {
        this.dailyStatus.put(date, DateStatusEnum.UNAVAILABLE);
    }

}
