package org.travelling.ticketer.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.travelling.ticketer.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Repository
public interface TravelConnectionDAO extends JpaRepository<Connection, Long> {
    Set<Connection> findConnectionsByDepartureTimeGreaterThanEqualAndFromStationAndToStation(LocalTime time, String fromStation, String toStation);
}
