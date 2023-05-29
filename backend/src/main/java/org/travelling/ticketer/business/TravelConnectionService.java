package org.travelling.ticketer.business;

import org.springframework.stereotype.Service;
import org.travelling.ticketer.dao.TravelConnectionDAO;
import org.travelling.ticketer.dto.SeatsDTO;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Train;
import org.travelling.ticketer.mapper.ConnectionMapper;
import org.travelling.ticketer.utility.ExceptionBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TravelConnectionService {

    private final TravelConnectionDAO travelConnectionDAO;

    private final SeatsService seatsService;

    private final ConnectionMapper connectionMapper;

    @Autowired
    public TravelConnectionService(TravelConnectionDAO travelConnectionDAO, SeatsService seatsService, ConnectionMapper connectionMapper) {
        this.travelConnectionDAO = travelConnectionDAO;
        this.seatsService = seatsService;
        this.connectionMapper = connectionMapper;
    }

    public Set<SeatsDTO> getConnectionsWithFreeSeats(String from, String to, LocalDateTime travelDateTime) {
        Set<Connection> connections = travelConnectionDAO.findConnectionsByDepartureTimeGreaterThanEqualAndFromStationAndToStation(travelDateTime.toLocalTime(), from, to);
        return connections.stream().map(connection -> seatsService.getSeat(travelDateTime, connection, from, to)).filter(Objects::nonNull).collect(Collectors.toSet());
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
