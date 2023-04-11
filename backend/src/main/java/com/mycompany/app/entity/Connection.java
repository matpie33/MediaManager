package com.mycompany.app.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String fromStation;

    @Column(nullable = false)
    private String toStation;

    @Column(nullable = false)
    private Time time;

    @Column(nullable = false)
    private int freeSeats;

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
