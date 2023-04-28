package org.travelling.ticketer.dto;

public class SeatsDTO {

    private String time;

    private int freeSeats;

    private String fromStation;
    private String toStation;

    private long id;

    public SeatsDTO setFromStation(String fromStation) {
        this.fromStation = fromStation;
        return this;
    }

    public SeatsDTO setToStation(String toStation) {
        this.toStation = toStation;
        return this;
    }

    public SeatsDTO setTime(String time) {
        this.time = time;
        return this;
    }

    public SeatsDTO setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
        return this;
    }

    public void setId(long id) {
        this.id = id;
    }
}
