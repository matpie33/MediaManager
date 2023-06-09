package org.travelling.ticketer.business.notifications;

import org.springframework.stereotype.Service;
import org.travelling.ticketer.constants.NotificationType;
import org.travelling.ticketer.dao.TicketDao;
import org.travelling.ticketer.dto.ConnectionDelayAndUrlDTO;
import org.travelling.ticketer.dto.TicketForNotificationDTO;
import org.travelling.ticketer.entity.Ticket;
import org.travelling.ticketer.mapper.TicketMapper;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private  final EmailSendingService emailSendingService;
    private  final SmsSendingService smsSendingService;

    private final TicketDao ticketDao;

    private final TicketMapper ticketMapper;

    private Map<NotificationType, NotificationSender> notificationSenders = new HashMap<>();

    @PostConstruct
    public void initialize (){
        notificationSenders.put(NotificationType.EMAIL, emailSendingService);
        notificationSenders.put(NotificationType.SMS, smsSendingService);
    }

    public NotificationService(EmailSendingService emailSendingService, SmsSendingService smsSendingService, TicketDao ticketDao, TicketMapper ticketMapper) {
        this.emailSendingService = emailSendingService;
        this.smsSendingService = smsSendingService;
        this.ticketDao = ticketDao;
        this.ticketMapper = ticketMapper;
    }

    public void sendNotifications (ConnectionDelayAndUrlDTO connectionDelayAndUrlDTO){
        Set<TicketForNotificationDTO> ticketsForEmail = getTicketsAffectedByDelay(connectionDelayAndUrlDTO);
        for (TicketForNotificationDTO ticketForNotificationDTO : ticketsForEmail) {
            Set<NotificationType> acceptedNotificationTypes = ticketForNotificationDTO.getAcceptedNotificationTypes();
            for (NotificationType acceptedNotificationType : acceptedNotificationTypes) {
                notificationSenders.get(acceptedNotificationType).send(ticketForNotificationDTO);
            }
        }
    }

    private Set<TicketForNotificationDTO> getTicketsAffectedByDelay(ConnectionDelayAndUrlDTO connectionDelayAndUrlDTO) {
        LocalDate travelDate = LocalDate.now();
        Set<Ticket> tickets = ticketDao.findByConnection_IdAndTravelDate(connectionDelayAndUrlDTO.getConnectionId(), travelDate);
        return tickets.stream().map(ticket -> ticketMapper.mapTicketForNotification(ticket, connectionDelayAndUrlDTO)).collect(Collectors.toSet());
    }

}
