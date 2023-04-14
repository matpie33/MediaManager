package org.media.manager.dao;

import org.media.manager.entity.AppUser;
import org.media.manager.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TicketDao extends JpaRepository<Ticket, Long> {

    Set<Ticket> findByAppUser_IdOrderByTravelDateAsc(long userId);
}
