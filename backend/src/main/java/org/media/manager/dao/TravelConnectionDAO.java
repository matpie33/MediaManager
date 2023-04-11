package org.media.manager.dao;

import org.media.manager.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.Set;

public interface TravelConnectionDAO extends JpaRepository<Connection, Long> {
    Set<Connection> findConnectionsByTimeGreaterThanEqualAndFromStationAndToStation(Time time, String fromStation, String toStation);
}
