package org.travelling.ticketer.dto;

public class TicketForEmailDTO {

    private String urlWithDelay;

    private String fromStation;

    private String toStation;

    private String departureTime;

    private String date;

    private int delay;

    private String recipient;


    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
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
