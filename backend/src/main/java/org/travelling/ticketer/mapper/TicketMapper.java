package org.travelling.ticketer.mapper;

import org.travelling.ticketer.dto.TicketDTO;
import org.travelling.ticketer.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}
