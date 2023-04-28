package org.travelling.ticketer.dto;

import javax.persistence.Column;
import java.sql.Time;
import java.time.LocalTime;

public class ConnectionDTO {

    private long id;

    private String fromStation;

    private String toStation;

    private String time;

    private int freeSeats;

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(long id) {
        this.id = id;
    }
}
