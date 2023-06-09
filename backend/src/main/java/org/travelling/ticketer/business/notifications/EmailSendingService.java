package org.travelling.ticketer.business.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.travelling.ticketer.business.DelayMessageProvider;
import org.travelling.ticketer.dto.TicketForNotificationDTO;

import java.util.Set;

@Service
public class EmailSendingService implements NotificationSender{

    private final JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String sender;

    private static final String SUBJECT = "Your train has delays";

    private final DelayMessageProvider delayMessageProvider;

    @Autowired
    public EmailSendingService(JavaMailSender javaMailSender, DelayMessageProvider delayMessageProvider) {
        this.javaMailSender = javaMailSender;
        this.delayMessageProvider = delayMessageProvider;
    }

    private SimpleMailMessage createMessage( TicketForNotificationDTO ticketWithDelayDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject(SUBJECT);
        simpleMailMessage.setTo(ticketWithDelayDTO.getRecipientEmail());
        simpleMailMessage.setText(delayMessageProvider.provideMessage(ticketWithDelayDTO));
        return simpleMailMessage;
    }

    @Override
    public void send(TicketForNotificationDTO ticketData) {
        SimpleMailMessage message = createMessage(ticketData);
        javaMailSender.send(message);
    }
}
