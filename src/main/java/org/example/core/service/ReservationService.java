package org.example.core.service;

import org.example.core.model.Desk;
import org.example.core.model.Office;
import org.example.core.model.Reservation;
import org.example.core.repository.DeskRepository;
import org.example.core.repository.EmployeeRepository;
import org.example.core.repository.ReservationRepository;
import org.example.restapi.dto.ReservationDto;
import org.example.restapi.dto.create.ReservationCreateDto;
import org.example.restapi.mapper.ReservationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationService extends GenericService<Reservation, ReservationDto> {
    private ReservationRepository reservationRepository;
    private DeskRepository deskRepository;
    private EmployeeRepository employeeRepository;
    private ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository,  DeskRepository deskRepository, EmployeeRepository employeeRepository,  ReservationMapper reservationMapper) {
        super(reservationRepository);
        this.reservationRepository = reservationRepository;
        this.deskRepository = deskRepository;
        this.employeeRepository = employeeRepository;
        this.reservationMapper = reservationMapper;
    }

    public List<ReservationDto> getReservationsByEmployeeEmail(String employeeEmail) {
        var employee = employeeRepository.findByEmail(employeeEmail);

        var reservations = reservationRepository.findByEmployeeUuidAndStartDateAfter(
                employee.getUuid(), java.time.LocalDate.now().atStartOfDay()
        );

        reservations.forEach(reservation -> {
            Desk desk = reservation.getDesk();
            if (desk != null) {
                Office office = desk.getOffice();
                if (office != null) {
                    office.setDesks(null);
                }
            }
        });

        return reservationMapper.toReservationDtos(reservations);
    }

    public List<ReservationDto> getReservations() {
        return  reservationMapper.toReservationDtos(reservationRepository.findAll());
    }

    public ReservationDto createReservation(ReservationCreateDto dto) {
        var desk = deskRepository.findById(UUID.fromString(dto.getDeskId()))
                .orElseThrow(() -> new IllegalArgumentException("Desk not found"));

        var employee = employeeRepository.findById(UUID.fromString(dto.getEmployeeId()))
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        if (dto.getStartDate() == null || dto.getEndDate() == null)
            throw new IllegalArgumentException("Start and end dates must be provided");
        if (dto.getStartDate().isAfter(dto.getEndDate()))
            throw new IllegalArgumentException("Start date cannot be after end date");

        boolean overlap = reservationRepository.existsOverlap(
                UUID.fromString(dto.getDeskId()),
                dto.getStartDate(),
                dto.getEndDate()
        );

        if (overlap)
            throw new IllegalStateException("Desk is already reserved for that period");

        Reservation reservation = new Reservation();
        reservation.setDesk(desk);
        reservation.setEmployee(employee);
        reservation.setStartDate(dto.getStartDate());
        reservation.setEndDate(dto.getEndDate());

        var createdReservation =create(reservation);

        var mapped = reservationMapper.toReservationDto(createdReservation);

        return mapped;
    }
}
