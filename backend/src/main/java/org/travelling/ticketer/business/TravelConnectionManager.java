package org.travelling.ticketer.business;

import org.travelling.ticketer.dao.TravelConnectionDAO;
import org.travelling.ticketer.dto.SeatsDTO;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Train;
import org.travelling.ticketer.mapper.ConnectionMapper;
import org.travelling.ticketer.utility.ExceptionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TravelConnectionManager {

    private TravelConnectionDAO travelConnectionDAO;

    private SeatsManager seatsManager;

    private ConnectionMapper connectionMapper;

    @Autowired
    public TravelConnectionManager(TravelConnectionDAO travelConnectionDAO, SeatsManager seatsManager, ConnectionMapper connectionMapper) {
        this.travelConnectionDAO = travelConnectionDAO;
        this.seatsManager = seatsManager;
        this.connectionMapper = connectionMapper;
    }

    public Set<SeatsDTO> getConnectionsWithFreeSeats(String from, String to, LocalDateTime travelDateTime) {
        Set<Connection> connections = travelConnectionDAO.findConnectionsByTimeGreaterThanEqualAndFromStationAndToStation(travelDateTime.toLocalTime(), from, to);
        return connections.stream().map(connection -> seatsManager.getSeat(travelDateTime, connection, from, to)).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public Connection getConnectionById(long connectionId){
        return travelConnectionDAO.findById(connectionId).orElseThrow(ExceptionBuilder.createIllegalArgumentException("Travel connection does not exist"));
    }

    public void addNewConnection (String from,String to, String time,
                                  Train train){
        Connection connection = connectionMapper.mapConnection(from, to, time, train);
        travelConnectionDAO.save(connection);
    }


}
