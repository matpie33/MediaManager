package org.travelling.ticketer.dto;

public class PdfTicketInputDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String travelDate;
    private String fromStation;
    private String toStation;
    private String ticketType;
    private String trainName;
    private String trainTime;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainTime() {
        return trainTime;
    }
}
