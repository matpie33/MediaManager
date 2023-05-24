package org.travelling.ticketer.business;

import org.travelling.ticketer.constants.TicketType;
import org.travelling.ticketer.dao.TicketDao;
import org.travelling.ticketer.dto.TicketCheckDTO;
import org.travelling.ticketer.dto.TicketDTO;
import org.travelling.ticketer.dto.TicketPdfDTO;
import org.travelling.ticketer.entity.AppUser;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Ticket;
import org.travelling.ticketer.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.travelling.ticketer.security.SecurityManager;
import org.travelling.ticketer.utility.ExceptionBuilder;

import javax.crypto.spec.IvParameterSpec;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TicketsManager {

    private final TicketDao ticketDao;

    private final AppUserManager appUserManager;

    private final TicketMapper ticketMapper;

    private final SecurityManager securityManager;

    @Autowired
    public TicketsManager(SecurityManager securityManager, TicketDao ticketDao, AppUserManager appUserManager, TicketMapper ticketMapper) {
        this.ticketDao = ticketDao;
        this.appUserManager = appUserManager;
        this.ticketMapper = ticketMapper;
        this.securityManager = securityManager;
    }

    public String getIv(long ticketId){
        return getTicket(ticketId).getInitializationVector();
    }

    public void assignTicketToUser(long userId, String ticketType, LocalDateTime dateTime, Connection connection) {
        AppUser user = appUserManager.getUserById(userId);
        Ticket ticket = new Ticket();
        ticket.setTicketType(TicketType.fromString(ticketType));
        ticket.setConnection(connection);
        ticket.setAppUser(user);
        ticket.setTravelDate(dateTime.toLocalDate());
        IvParameterSpec iv = securityManager.generateIv();
        ticket.setInitializationVector(securityManager.convertIvToString(iv));
        ticketDao.save(ticket);
    }

    public Ticket getTicket(long id){
        return ticketDao.findById(id).orElseThrow(ExceptionBuilder.createIllegalArgumentException("ticket not found"));
    }

    public TicketPdfDTO getTicketForPdf(Ticket ticket){
        return ticketMapper.mapTicketForPdf(ticket);
    }

    public TicketCheckDTO getTicketForChecking(long ticketId){
        return ticketMapper.mapTicketForChecking(ticketDao.findById(ticketId).orElseThrow(ExceptionBuilder.createIllegalArgumentException("Ticket not found")));
    }

    public LinkedHashSet<TicketDTO> getTicketsOfUser(long userId){
        Set<Ticket> tickets = ticketDao.findByAppUser_IdOrderByTravelDateAscConnection_departureTimeAsc(userId);
        return tickets.stream().map(ticketMapper::mapTicket).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<TicketDTO> getTicketsOfUserValidNow(long userId){
        Set<Ticket> tickets = ticketDao.findByAppUser_Id(userId);
        LocalDateTime dateTimeNow = LocalDateTime.now();
        return tickets.stream().filter(ticket-> isTicketValidNow(dateTimeNow, ticket)).map(ticketMapper::mapTicket).collect(Collectors.toSet());
    }

    private boolean isTicketValidNow(LocalDateTime dateTimeNow, Ticket ticket) {
        return LocalDateTime.of(ticket.getTravelDate(), ticket.getConnection().getDepartureTime()).isBefore(dateTimeNow)
                && LocalDateTime.of(ticket.getTravelDate(), ticket.getConnection().getArrivalTime()).isAfter(dateTimeNow);
    }


}
