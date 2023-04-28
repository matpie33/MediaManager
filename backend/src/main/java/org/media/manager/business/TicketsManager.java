package org.media.manager.business;

import org.media.manager.constants.TicketType;
import org.media.manager.dao.TicketDao;
import org.media.manager.dto.TicketDTO;
import org.media.manager.entity.AppUser;
import org.media.manager.entity.Connection;
import org.media.manager.entity.Ticket;
import org.media.manager.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TicketsManager {

    private TicketDao ticketDao;

    private AppUserManager appUserManager;

    private TicketMapper ticketMapper;

    @Autowired
    public TicketsManager(TicketDao ticketDao, AppUserManager appUserManager, TicketMapper ticketMapper) {
        this.ticketDao = ticketDao;
        this.appUserManager = appUserManager;
        this.ticketMapper = ticketMapper;
    }

    public void assignTicketToUser(long userId, String ticketType, LocalDateTime dateTime, Connection connection) {
        AppUser user = appUserManager.getUserById(userId);
        Ticket ticket = new Ticket();
        ticket.setTicketType(TicketType.fromString(ticketType));
        ticket.setConnection(connection);
        ticket.setAppUser(user);
        ticket.setTravelDate(dateTime.toLocalDate());
        ticketDao.save(ticket);
    }

    public LinkedHashSet<TicketDTO> getTicketsOfUser(long userId){
        Set<Ticket> tickets = ticketDao.findByAppUser_IdOrderByTravelDateAscConnection_timeAsc(userId);
        LinkedHashSet<TicketDTO> ticketsOfUser = tickets.stream().map(ticketMapper::mapTicket).collect(Collectors.toCollection(LinkedHashSet::new));
        return ticketsOfUser;
    }

}
