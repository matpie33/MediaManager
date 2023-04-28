package org.travelling.ticketer.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Seats {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private Connection connection;

    @Column
    private int freeSeats;

    @Column
    private LocalDate date;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public void setDate(LocalDate dateOfTravel) {
        this.date = dateOfTravel;
    }

    public void decreaseFreeSeatAmount(){
        freeSeats--;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public LocalDate getDate() {
        return date;
    }

    public Connection getConnection() {
        return connection;
    }
}
