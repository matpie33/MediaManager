package org.travelling.ticketer.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.travelling.ticketer.dto.TicketForEmailDTO;

import java.util.Set;

@Service
public class EmailSendingService {

    private final JavaMailSender javaMailSender;

    public static final String MESSAGE_PATTERN = "Hello. The train on your connection from %s to %s at %s" +
            " is delayed %d minutes. You can check the current delay on %s";
    @Value("${spring.mail.username}")
    private String sender;

    private static final String SUBJECT = "Your train has delays";

    @Autowired
    public EmailSendingService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmails (Set<TicketForEmailDTO> ticketsForEmails){
        ticketsForEmails.stream().map(this::createMessage).forEach(javaMailSender::send);
    }

    private SimpleMailMessage createMessage( TicketForEmailDTO ticketWithDelayDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject(SUBJECT);
        simpleMailMessage.setTo(ticketWithDelayDTO.getRecipient());
        simpleMailMessage.setText(String.format(MESSAGE_PATTERN, ticketWithDelayDTO.getFromStation(),
                ticketWithDelayDTO.getToStation(), ticketWithDelayDTO.getDepartureTime(),
                ticketWithDelayDTO.getDelay(), ticketWithDelayDTO.getUrlWithDelay()));
        return simpleMailMessage;
    }

}
