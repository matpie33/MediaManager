package org.media.manager.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String fromStation;

    @Column(nullable = false)
    private String toStation;

    @Column(nullable = false)
    private Time time;

    @Column(nullable = false)
    private int freeSeats;

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public Time getTime() {
        return time;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id=" + id +
                ", fromStation='" + fromStation + '\'' +
                ", toStation='" + toStation + '\'' +
                ", time=" + time +
                ", freeSeats=" + freeSeats +
                '}';
    }
}
