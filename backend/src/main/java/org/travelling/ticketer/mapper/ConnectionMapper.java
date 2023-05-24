package org.travelling.ticketer.mapper;

import org.travelling.ticketer.constants.DateTimeFormats;
import org.travelling.ticketer.dto.ConnectionDTO;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Train;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class ConnectionMapper {

    public ConnectionDTO mapConnection (Connection connection){
        ConnectionDTO connectionDTO = new ConnectionDTO();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.TIME_FORMAT);
        connectionDTO.setTime(dateTimeFormatter.format(connection.getDepartureTime()));
        connectionDTO.setFromStation(connection.getFromStation());
        connectionDTO.setToStation(connection.getToStation());
        connectionDTO.setId(connection.getId());
        return connectionDTO;
    }

    public Connection mapConnection (String fromStation, String toStation, String time, Train train){
        Connection connection = new Connection();
        LocalTime localTime = LocalTime.parse(time);
        connection.setDepartureTime(localTime);
        connection.setFromStation(fromStation);
        connection.setToStation(toStation);
        connection.setTrain(train);
        return connection;
    }

}
