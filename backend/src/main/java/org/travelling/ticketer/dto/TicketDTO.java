package org.travelling.ticketer.dto;

public class TicketDTO {

    private String travelDate;

    private String ticketType;

    private String trainName;

    private ConnectionDTO connection;

    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

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
