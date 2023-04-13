package org.media.manager.mapper;

import org.media.manager.dto.ConnectionDTO;
import org.media.manager.entity.Connection;
import org.springframework.stereotype.Component;

@Component
public class ConnectionMapper {

    public ConnectionDTO mapConnection (Connection connection){
        ConnectionDTO connectionDTO = new ConnectionDTO();
        connectionDTO.setTime(connection.getTime());
        connectionDTO.setFromStation(connection.getFromStation());
        connectionDTO.setToStation(connection.getToStation());
        return connectionDTO;
    }

}
