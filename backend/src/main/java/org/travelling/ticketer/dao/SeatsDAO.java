package org.travelling.ticketer.dao;

import org.travelling.ticketer.entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SeatsDAO extends JpaRepository<Seats, Long> {

    Optional<Seats> findByDateAndConnection_Id(LocalDate dateTime, Long connectionID);
    Optional<Seats> findByConnection_IdAndAndDate(long connectionId, LocalDate date);
}