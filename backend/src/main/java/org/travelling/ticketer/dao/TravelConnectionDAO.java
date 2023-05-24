package org.travelling.ticketer.dao;

import org.travelling.ticketer.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Set;

public interface TravelConnectionDAO extends JpaRepository<Connection, Long> {
    Set<Connection> findConnectionsByDepartureTimeGreaterThanEqualAndFromStationAndToStation(LocalTime time, String fromStation, String toStation);
}
