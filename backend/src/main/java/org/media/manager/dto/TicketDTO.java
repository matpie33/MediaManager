package org.media.manager.dto;

import org.media.manager.enums.TicketType;

import java.sql.Date;

public class TicketDTO {

    private String travelDate;

    private String ticketType;

    private ConnectionDTO connection;

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public void setConnection(ConnectionDTO connection) {
        this.connection = connection;
    }
}
