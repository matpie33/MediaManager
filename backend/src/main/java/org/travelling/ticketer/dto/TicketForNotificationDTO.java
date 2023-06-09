package org.travelling.ticketer.dto;

import org.travelling.ticketer.constants.NotificationType;

import java.util.Set;

public class TicketForNotificationDTO {

    private String urlWithDelay;

    private String fromStation;

    private String toStation;

    private String departureTime;

    private String date;

    private int delay;

    private String recipientEmail;

    private String recipientPhoneNumber;

    private Set<NotificationType> acceptedNotificationTypes;

    public Set<NotificationType> getAcceptedNotificationTypes() {
        return acceptedNotificationTypes;
    }

    public void setRecipientPhoneNumber(String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public void setAcceptedNotificationTypes(Set<NotificationType> acceptedNotificationTypes) {
        this.acceptedNotificationTypes = acceptedNotificationTypes;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getUrlWithDelay() {
        return urlWithDelay;
    }

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public String getDate() {
        return date;
    }

    public int getDelay() {
        return delay;
    }

    public void setUrlWithDelay(String urlWithDelay) {
        this.urlWithDelay = urlWithDelay;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
