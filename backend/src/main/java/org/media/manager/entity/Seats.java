package org.media.manager.entity;

import javax.persistence.*;
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
    private LocalDateTime dateTimeOfTravel;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public void setDateTimeOfTravel(LocalDateTime dateOfTravel) {
        this.dateTimeOfTravel = dateOfTravel;
    }

    public void decreaseFreeSeatAmount(){
        freeSeats--;
    }

    public int getFreeSeats() {
        return freeSeats;
    }
}
