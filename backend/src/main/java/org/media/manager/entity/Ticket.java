package org.media.manager.entity;

import org.media.manager.enums.TicketType;

import javax.persistence.*;
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
    private LocalDateTime travelDate;

    public void setTravelDate(LocalDateTime travelDate) {
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

    public LocalDateTime getTravelDate() {
        return travelDate;
    }
}
