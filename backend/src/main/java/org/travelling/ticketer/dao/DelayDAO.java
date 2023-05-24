package org.travelling.ticketer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.travelling.ticketer.entity.Delay;

import java.time.LocalDate;
import java.util.Optional;

public interface DelayDAO extends JpaRepository<Delay, Long> {
    Optional<Delay> findByConnection_idAndDate(long connectionId, LocalDate date);
}
