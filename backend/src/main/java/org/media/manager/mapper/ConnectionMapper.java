package org.media.manager.mapper;

import org.media.manager.constants.DateTimeFormats;
import org.media.manager.dto.ConnectionDTO;
import org.media.manager.entity.Connection;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

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

}
