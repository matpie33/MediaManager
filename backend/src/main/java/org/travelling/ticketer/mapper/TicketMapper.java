package org.travelling.ticketer.mapper;

import org.jfree.chart.axis.Tick;
import org.travelling.ticketer.constants.DateTimeFormats;
import org.travelling.ticketer.dto.*;
import org.travelling.ticketer.entity.AppUser;
import org.travelling.ticketer.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.travelling.ticketer.projections.TicketWithDelayView;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

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

    public TicketWithDelayDTO mapTicket (TicketWithDelayView ticketView){
        TicketWithDelayDTO ticketDTO = new TicketWithDelayDTO();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.TIME_FORMAT);
        ticketDTO.setArrivalTime(dateTimeFormatter.format(ticketView.getArrivalTime()));
        ticketDTO.setDelayMinutes(ticketView.getDelayMinutes());
        ticketDTO.setDepartureTime(dateTimeFormatter.format(ticketView.getDepartureTime()));
        ticketDTO.setFromStation(ticketView.getFromStation());
        ticketDTO.setToStation(ticketView.getToStation());
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

    public TicketForEmailDTO mapTicketForEmail (Ticket ticket, ConnectionDelayAndUrlDTO connectionDelayAndUrlDTO){
        TicketForEmailDTO ticketForEmailDTO = new TicketForEmailDTO();
        ticketForEmailDTO.setDelay(connectionDelayAndUrlDTO.getDelay());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.DATE_FORMAT);
        ticketForEmailDTO.setDate(dateTimeFormatter.format(ticket.getTravelDate()));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.TIME_FORMAT);
        ticketForEmailDTO.setDepartureTime(timeFormatter.format(ticket.getConnection().getDepartureTime()));
        ticketForEmailDTO.setFromStation(ticket.getConnection().getFromStation());
        ticketForEmailDTO.setToStation(ticket.getConnection().getToStation());
        ticketForEmailDTO.setUrlWithDelay(connectionDelayAndUrlDTO.getUrl());
        ticketForEmailDTO.setRecipient(ticket.getAppUser().getEmail());
        return ticketForEmailDTO;
    }


}
