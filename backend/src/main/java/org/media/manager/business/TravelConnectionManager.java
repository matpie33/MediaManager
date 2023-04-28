package org.media.manager.business;

import org.media.manager.dao.TravelConnectionDAO;
import org.media.manager.dto.SeatsDTO;
import org.media.manager.entity.Connection;
import org.media.manager.entity.Train;
import org.media.manager.mapper.ConnectionMapper;
import org.media.manager.utility.ExceptionBuilder;
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
