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

    public QrCodeStatusDTO getValidationStatus(QrCodeContentDTO qrCodeContentDTO){
        QrCodeStatusDTO qrCodeStatusDTO = new QrCodeStatusDTO();
        if (!qrCodeContentDTO.isValidContent()){
            qrCodeStatusDTO.setContent(TICKET_INCORRECT);
        }
        else{
            Ticket ticket = ticketsManager.getTicket(qrCodeContentDTO.getTicketId());
            if (isQrCodeDataValid(qrCodeContentDTO, ticket)) {
                qrCodeStatusDTO.setContent(TICKET_DATA_CORRECT);
            }
            else{
                qrCodeStatusDTO.setContent(TICKET_INCORRECT);
            }
        }
        return qrCodeStatusDTO;
    }

    private boolean isQrCodeDataValid(QrCodeContentDTO qrCodeContentDTO, Ticket ticket) {
        return ticket.getConnection().getId().equals(qrCodeContentDTO.getConnectionId()) && ticket.getAppUser().getId().equals(qrCodeContentDTO.getUserId());
    }
}
