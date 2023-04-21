package org.media.manager.entity;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;

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
    private LocalTime time;

    @ManyToOne
    private Train train;

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public LocalTime getTime() {
        return time;
    }


    public Long getId() {
        return id;
    }

    public Train getTrain() {
        return train;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id=" + id +
                ", fromStation='" + fromStation + '\'' +
                ", toStation='" + toStation + '\'' +
                ", time=" + time +
                '}';
    }
}
