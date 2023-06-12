package org.travelling.ticketer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelling.ticketer.constants.NotificationType;
import org.travelling.ticketer.entity.AppUser;
import org.travelling.ticketer.entity.Notification;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface NotificationDAO extends JpaRepository<Notification, Long> {

    Set<Notification> findByNotificationTypeIn(Collection<NotificationType> notificationTypes);
}
