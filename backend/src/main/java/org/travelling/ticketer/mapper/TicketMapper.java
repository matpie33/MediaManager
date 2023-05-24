package org.travelling.ticketer.mapper;

import org.travelling.ticketer.constants.DateTimeFormats;
import org.travelling.ticketer.dto.TicketCheckDTO;
import org.travelling.ticketer.dto.TicketDTO;
import org.travelling.ticketer.dto.TicketPdfDTO;
import org.travelling.ticketer.entity.AppUser;
import org.travelling.ticketer.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class TicketMapper {

    private final ConnectionMapper connectionMapper;

    @Autowired
    public TicketMapper(ConnectionMapper connectionMapper) {
        this.connectionMapper = connectionMapper;
    }

    public TicketDTO mapTicket (Ticket ticket){
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketType(ticket.getTicketType().getDisplayName());
        ticketDTO.setTravelDate(ticket.getTravelDate().toString());
        ticketDTO.setConnection(connectionMapper.mapConnection(ticket.getConnection()));
        ticketDTO.setTrainName(ticket.getConnection().getTrain().getName());
        ticketDTO.setId(ticket.getId());
        return ticketDTO;
    }

    public TicketCheckDTO mapTicketForChecking (Ticket ticket){
        TicketCheckDTO ticketCheckDTO = new TicketCheckDTO();
        ticketCheckDTO.setTicketType(ticket.getTicketType().getDisplayName());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.DATE_FORMAT);
        ticketCheckDTO.setDate(dateTimeFormatter.format(ticket.getTravelDate()));
        ticketCheckDTO.setTime(ticket.getConnection().getDepartureTime().toString());
        ticketCheckDTO.setFirstName(ticket.getAppUser().getFirstName());
        ticketCheckDTO.setLastName(ticket.getAppUser().getLastName());
        ticketCheckDTO.setFromStation(ticket.getConnection().getFromStation());
        ticketCheckDTO.setToStation(ticket.getConnection().getToStation());
        return ticketCheckDTO;
    }

    public TicketPdfDTO mapTicketForPdf (Ticket ticket){
        TicketPdfDTO ticketCheckDTO = new TicketPdfDTO();
        ticketCheckDTO.setTicketType(ticket.getTicketType().getDisplayName());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.DATE_FORMAT);
        ticketCheckDTO.setTravelDate(dateTimeFormatter.format(ticket.getTravelDate()));
        ticketCheckDTO.setTravelTime(ticket.getConnection().getDepartureTime().toString());
        AppUser appUser = ticket.getAppUser();
        ticketCheckDTO.setName(appUser.getFirstName() + " " + appUser.getLastName());
        ticketCheckDTO.setEmail(appUser.getEmail());
        ticketCheckDTO.setFromStation(ticket.getConnection().getFromStation());
        ticketCheckDTO.setToStation(ticket.getConnection().getToStation());
        ticketCheckDTO.setTrain(ticket.getConnection().getTrain().getName());
        return ticketCheckDTO;
    }

}
