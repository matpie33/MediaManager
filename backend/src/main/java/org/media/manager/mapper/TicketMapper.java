package org.media.manager.mapper;

import org.media.manager.dto.TicketDTO;
import org.media.manager.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    @Autowired
    private ConnectionMapper connectionMapper;

    public TicketDTO mapTicket (Ticket ticket){
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketType(ticket.getTicketType());
        ticketDTO.setTravelDate(ticket.getTravelDate());
        ticketDTO.setConnection(connectionMapper.mapConnection(ticket.getConnection()));
        return ticketDTO;
    }

}
