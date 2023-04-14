package org.media.manager.enums;

import java.util.Arrays;

public enum TicketType {
    FULL_FARE("full-fare"), REDUCED("reduced");
    private final String displayName;

    TicketType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TicketType fromString(String text){
        return Arrays.stream(values()).filter(nextEnum->nextEnum.displayName.equals(text))
                .findFirst().orElseThrow(()->new IllegalArgumentException("Not found enum"));
    }

}
