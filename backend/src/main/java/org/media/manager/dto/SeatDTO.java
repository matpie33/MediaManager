package org.media.manager.dto;

public class SeatDTO {

    private String time;

    private int freeSeats;

    private String fromStation;
    private String toStation;

    private long id;

    public SeatDTO setFromStation(String fromStation) {
        this.fromStation = fromStation;
        return this;
    }

    public SeatDTO setToStation(String toStation) {
        this.toStation = toStation;
        return this;
    }

    public SeatDTO setTime(String time) {
        this.time = time;
        return this;
    }

    public SeatDTO setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
        return this;
    }

    public void setId(long id) {
        this.id = id;
    }
}
