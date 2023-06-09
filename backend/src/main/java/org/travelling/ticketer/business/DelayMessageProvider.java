package org.travelling.ticketer.business;

import org.springframework.stereotype.Component;
import org.travelling.ticketer.dto.TicketForNotificationDTO;

@Component
public class DelayMessageProvider {

    private static final String MESSAGE_PATTERN = "Hello. The train on your connection from %s to %s at %s" +
            " is delayed %d minutes. You can check the current delay on %s";

    public String provideMessage (TicketForNotificationDTO ticketWithDelayDTO){
        return String.format(MESSAGE_PATTERN, ticketWithDelayDTO.getFromStation(),
        ticketWithDelayDTO.getToStation(), ticketWithDelayDTO.getDepartureTime(),
        ticketWithDelayDTO.getDelay(), ticketWithDelayDTO.getUrlWithDelay());
    }

}
