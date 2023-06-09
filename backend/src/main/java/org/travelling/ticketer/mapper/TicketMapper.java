package org.travelling.ticketer.mapper;

import org.travelling.ticketer.constants.DateTimeFormats;
import org.travelling.ticketer.constants.NotificationType;
import org.travelling.ticketer.dto.*;
import org.travelling.ticketer.entity.AppUser;
import org.travelling.ticketer.entity.Notification;
import org.travelling.ticketer.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.travelling.ticketer.projections.TicketWithDelayView;

import java.time.format.DateTimeFormatter;
import java.util.Set;
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

    public TicketForNotificationDTO mapTicketForNotification(Ticket ticket, ConnectionDelayAndUrlDTO connectionDelayAndUrlDTO){
        TicketForNotificationDTO ticketForNotificationDTO = new TicketForNotificationDTO();
        ticketForNotificationDTO.setDelay(connectionDelayAndUrlDTO.getDelay());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.DATE_FORMAT);
        ticketForNotificationDTO.setDate(dateTimeFormatter.format(ticket.getTravelDate()));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.TIME_FORMAT);
        ticketForNotificationDTO.setDepartureTime(timeFormatter.format(ticket.getConnection().getDepartureTime()));
        ticketForNotificationDTO.setFromStation(ticket.getConnection().getFromStation());
        ticketForNotificationDTO.setToStation(ticket.getConnection().getToStation());
        ticketForNotificationDTO.setUrlWithDelay(connectionDelayAndUrlDTO.getUrl());
        ticketForNotificationDTO.setRecipientEmail(ticket.getAppUser().getEmail());
        ticketForNotificationDTO.setAcceptedNotificationTypes(getAcceptedNotificationTypes(ticket));
        ticketForNotificationDTO.setRecipientPhoneNumber(ticket.getAppUser().getPhoneNumber());
        return ticketForNotificationDTO;
    }

    private static Set<NotificationType> getAcceptedNotificationTypes(Ticket ticket) {
        return ticket.getAppUser().getAcceptedNotificationTypes().stream().map(Notification::getNotificationType).collect(Collectors.toSet());
    }


}
