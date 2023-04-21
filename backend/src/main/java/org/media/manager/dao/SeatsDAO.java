package org.media.manager.dao;

import org.media.manager.entity.Seats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SeatsDAO extends JpaRepository<Seats, Long> {

    Optional<Seats> findByDateTimeOfTravelAndConnection_Id(LocalDateTime dateTime, Long connectionID);
}
