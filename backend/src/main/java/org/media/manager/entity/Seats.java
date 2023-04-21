package org.media.manager.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDate dateTimeOfTravel;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public void setDateTimeOfTravel(LocalDate dateOfTravel) {
        this.dateTimeOfTravel = dateOfTravel;
    }

    public void decreaseFreeSeatAmount(){
        freeSeats--;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public LocalDate getDateTimeOfTravel() {
        return dateTimeOfTravel;
    }

    public Connection getConnection() {
        return connection;
    }
}
