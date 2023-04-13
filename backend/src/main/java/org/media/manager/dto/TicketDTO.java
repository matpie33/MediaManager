package org.media.manager.dto;

import org.media.manager.enums.TicketType;

import java.sql.Date;

public class TicketDTO {

    private Date travelDate;

    private TicketType ticketType;

    private ConnectionDTO connection;

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public void setConnection(ConnectionDTO connection) {
        this.connection = connection;
    }
}
