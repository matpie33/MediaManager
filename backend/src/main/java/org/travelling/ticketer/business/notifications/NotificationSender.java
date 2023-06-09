package org.travelling.ticketer.business.notifications;

import org.travelling.ticketer.dto.TicketForNotificationDTO;

import java.util.Set;

public interface NotificationSender {

    void send (TicketForNotificationDTO ticketsForEmails);
}
