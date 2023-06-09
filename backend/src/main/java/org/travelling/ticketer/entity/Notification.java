package org.travelling.ticketer.entity;

import org.travelling.ticketer.constants.NotificationType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Notification {
    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public NotificationType getNotificationType() {
        return notificationType;
    }
}
