package org.travelling.ticketer.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.travelling.ticketer.constants.NotificationType;
import org.travelling.ticketer.dao.NotificationDAO;
import org.travelling.ticketer.dto.*;
import org.travelling.ticketer.entity.Notification;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationTypesService {


    private final NotificationDAO notificationDAO;

    @Autowired
    public NotificationTypesService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    public Set<Notification> getNotificationTypesFromDB (UserPersonalDTO userPersonalDTO){
        Set<NotificationType> notificationTypes = userPersonalDTO.getAcceptedNotificationTypes().stream().map(NotificationType::valueOf).collect(Collectors.toSet());
        return notificationDAO.findByNotificationTypeIn(notificationTypes);
    }

}
