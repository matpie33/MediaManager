package org.media.manager.mapper;

import org.media.manager.constants.DateTimeFormats;
import org.media.manager.dto.ConnectionDTO;
import org.media.manager.entity.Connection;
import org.media.manager.entity.Train;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class ConnectionMapper {

    public ConnectionDTO mapConnection (Connection connection){
        ConnectionDTO connectionDTO = new ConnectionDTO();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.TIME_FORMAT);
        connectionDTO.setTime(dateTimeFormatter.format(connection.getTime()));
        connectionDTO.setFromStation(connection.getFromStation());
        connectionDTO.setToStation(connection.getToStation());
        connectionDTO.setId(connection.getId());
        return connectionDTO;
    }

    public Connection mapConnection (String fromStation, String toStation, String time, Train train){
        Connection connection = new Connection();
        LocalTime localTime = LocalTime.parse(time);
        connection.setTime(localTime);
        connection.setFromStation(fromStation);
        connection.setToStation(toStation);
        connection.setTrain(train);
        return connection;
    }

}
