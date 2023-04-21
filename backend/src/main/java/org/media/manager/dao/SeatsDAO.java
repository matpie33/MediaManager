package org.media.manager.dao;

import org.media.manager.entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface SeatsDAO extends JpaRepository<Seats, Long> {

    Optional<Seats> findByDateTimeOfTravelAndConnection_Id(LocalDate dateTime, Long connectionID);
    Optional<Seats> findByConnection_IdAndFreeSeatsGreaterThanAndDateTimeOfTravel(long connectionId, int freeSeats, LocalDate date);
}
