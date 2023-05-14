package org.travelling.ticketer.business;

import org.springframework.stereotype.Component;
import org.travelling.ticketer.dto.QrCodeContentDTO;
import org.travelling.ticketer.dto.QrCodeStatusDTO;
import org.travelling.ticketer.entity.Ticket;

@Component
public class QrCodeValidator {

    public static final String TICKET_INCORRECT = "Ticket incorrect";
    public static final String TICKET_DATA_CORRECT = "Ticket data correct";
    private final TicketsManager ticketsManager;

    public QrCodeValidator(TicketsManager ticketsManager) {
        this.ticketsManager = ticketsManager;
    }

    public boolean getValidationStatus(QrCodeContentDTO qrCodeContentDTO){
        if (!qrCodeContentDTO.isValidContent()){
            return false;
        }
        Ticket ticket = ticketsManager.getTicket(qrCodeContentDTO.getTicketId());
        return isQrCodeDataValid(qrCodeContentDTO, ticket);
    }

    private boolean isQrCodeDataValid(QrCodeContentDTO qrCodeContentDTO, Ticket ticket) {
        return ticket.getConnection().getId().equals(qrCodeContentDTO.getConnectionId()) && ticket.getAppUser().getId().equals(qrCodeContentDTO.getUserId());
    }
}
