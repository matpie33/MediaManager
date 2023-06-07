package org.travelling.ticketer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelling.ticketer.entity.AppUser;
import org.travelling.ticketer.entity.Delay;

import java.util.Optional;

@Repository
public interface DelayDAO extends JpaRepository<Delay, Long> {

}
