package org.travelling.ticketer.business;

import org.springframework.stereotype.Component;
import org.travelling.ticketer.dto.QrCodeContentDTO;
import org.travelling.ticketer.entity.Ticket;

@Component
public class QrCodeValidator {

    private final TicketsService ticketsService;

    public QrCodeValidator(TicketsService ticketsService) {
        this.ticketsService = ticketsService;
    }

    public boolean getValidationStatus(QrCodeContentDTO qrCodeContentDTO){
        if (!qrCodeContentDTO.isValidContent()){
            return false;
        }
        Ticket ticket = ticketsService.getTicket(qrCodeContentDTO.getTicketId());
        return isQrCodeDataValid(qrCodeContentDTO, ticket);
    }

    private boolean isQrCodeDataValid(QrCodeContentDTO qrCodeContentDTO, Ticket ticket) {
        return ticket.getConnection().getId().equals(qrCodeContentDTO.getConnectionId()) && ticket.getAppUser().getId().equals(qrCodeContentDTO.getUserId());
    }
}
