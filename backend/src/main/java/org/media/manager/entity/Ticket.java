package org.media.manager.entity;

import org.media.manager.enums.TicketType;

import javax.persistence.*;

@Entity
public class Ticket {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Connection connection;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @OneToOne
    private AppUser appUser;

}
