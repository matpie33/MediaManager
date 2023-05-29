package org.travelling.ticketer.dao;

import org.springframework.stereotype.Repository;
import org.travelling.ticketer.entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SeatsDAO extends JpaRepository<Seats, Long> {

    Optional<Seats> findByDateAndConnection_Id(LocalDate dateTime, Long connectionID);
    Optional<Seats> findByConnection_IdAndDate(long connectionId, LocalDate date);
}
