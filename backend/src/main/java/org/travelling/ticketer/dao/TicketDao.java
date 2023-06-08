package org.travelling.ticketer.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.travelling.ticketer.projections.TicketWithDelayView;
import org.travelling.ticketer.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Long> {

    Set<Ticket> findByAppUser_IdOrderByTravelDateAscConnection_departureTimeAsc(long userId);
    @Query("select t.connection.fromStation as fromStation, t.connection.toStation as toStation, t.connection.departureTime as departureTime, t.connection.arrivalTime as arrivalTime, " +
            "d.delayMinutes as delayMinutes from Ticket t inner join Delay d on t.connection.id = d.connection.id " +
            "and t.travelDate = d.date where t.appUser.id = :userId and t.travelDate = :today")
    Set<TicketWithDelayView> findTicketsWithDelaysForUser(long userId, LocalDate today);

    Set<Ticket> findByConnection_IdAndTravelDate(long connectionId, LocalDate travelDate);


}
