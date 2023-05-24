package org.travelling.ticketer.entity;

import javax.persistence.*;
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

    @Column()
    private LocalTime departureTime;
    @Column()
    private LocalTime arrivalTime;

    @ManyToOne
    private Train train;

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }


    public Long getId() {
        return id;
    }

    public Train getTrain() {
        return train;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public void setDepartureTime(LocalTime time) {
        this.departureTime = time;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id=" + id +
                ", fromStation='" + fromStation + '\'' +
                ", toStation='" + toStation + '\'' +
                ", time=" + departureTime +
                '}';
    }
}
