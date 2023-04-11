package com.mycompany.app.dao;

import com.mycompany.app.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.Set;

public interface ConnectionDAO extends JpaRepository<Connection, Long> {
    Set<Connection> findConnectionsByTimeGreaterThanEqualAndFromStationAndToStation(Time time, String fromStation, String toStation);
}
