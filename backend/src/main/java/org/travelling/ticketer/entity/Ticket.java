package org.travelling.ticketer.entity;

import org.travelling.ticketer.constants.TicketType;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column
    private String initializationVector;

    public void setInitializationVector(String initializationVector) {
        this.initializationVector = initializationVector;
    }

    public Long getId() {
        return id;
    }

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

    public String getInitializationVector() {
        return initializationVector;
    }

    public AppUser getAppUser() {
        return appUser;
    }
}
