package org.travelling.ticketer.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.travelling.ticketer.business.notifications.EmailSendingService;
import org.travelling.ticketer.dao.*;
import org.travelling.ticketer.dto.*;
import org.travelling.ticketer.entity.*;
import org.travelling.ticketer.mapper.TicketMapper;
import org.travelling.ticketer.utility.ExceptionBuilder;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DelayService {

    private DelayDAO delayDAO;

    private TravelConnectionDAO travelConnectionDAO;

    @Autowired
    public DelayService(DelayDAO delayDAO, TravelConnectionDAO travelConnectionDAO, TicketDao ticketDao, EmailSendingService emailSendingService, TicketMapper ticketMapper) {
        this.delayDAO = delayDAO;
        this.travelConnectionDAO = travelConnectionDAO;
    }

    public void addDelay (int delayValue, LocalDate date, long connectionId){
        Delay delay = new Delay();
        Connection connection = travelConnectionDAO.findById(connectionId).orElseThrow(ExceptionBuilder.createIllegalArgumentException("Connection with id: " + connectionId + " not found."));
        delay.setConnection(connection);
        delay.setDelayMinutes(delayValue);
        delay.setDate(date);
        delayDAO.save(delay);
    }


}
