package org.media.manager.dto;

import javax.persistence.Column;
import java.sql.Time;

public class ConnectionDTO {
    private String fromStation;

    private String toStation;

    private Time time;

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
