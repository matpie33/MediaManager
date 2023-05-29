package org.travelling.ticketer.projections;

import java.time.LocalTime;

public interface TicketWithDelayView {
    String getFromStation() ;

    String getToStation() ;

    LocalTime getDepartureTime() ;

    LocalTime getArrivalTime();

    int getDelayMinutes() ;
}
