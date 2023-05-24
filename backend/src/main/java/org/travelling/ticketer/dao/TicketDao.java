package org.travelling.ticketer.dao;

import org.travelling.ticketer.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TicketDao extends JpaRepository<Ticket, Long> {

    Set<Ticket> findByAppUser_IdOrderByTravelDateAscConnection_departureTimeAsc(long userId);
    Set<Ticket> findByAppUser_Id(long userId);
}
