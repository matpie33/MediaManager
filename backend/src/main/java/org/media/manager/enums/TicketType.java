package org.media.manager.enums;

import java.util.Arrays;

public enum TicketType {
    FULL_FARE("full-fare"), REDUCED("reduced");
    private final String display;

    TicketType(String display) {
        this.display = display;
    }

    public static TicketType fromString(String text){
        return Arrays.stream(values()).filter(nextEnum->nextEnum.display.equals(text))
                .findFirst().orElseThrow(()->new IllegalArgumentException("Not found enum"));
    }

}
