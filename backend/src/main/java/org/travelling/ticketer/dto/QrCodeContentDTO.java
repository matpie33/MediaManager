package org.travelling.ticketer.dto;

public class QrCodeContentDTO {

    private long connectionId;
    private long userId;
    private long ticketId;
    private boolean validContent;

    public boolean isValidContent() {
        return validContent;
    }

    public void setValidContent(boolean validContent) {
        this.validContent = validContent;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(long connectionId) {
        this.connectionId = connectionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
