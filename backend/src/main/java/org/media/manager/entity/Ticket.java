package org.media.manager.entity;

import org.media.manager.constants.TicketType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Ticket {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Connection connection;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @OneToOne(fetch = FetchType.LAZY)
    private AppUser appUser;

    @Column
    private LocalDate travelDate;

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Connection getConnection() {
        return connection;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }
}
