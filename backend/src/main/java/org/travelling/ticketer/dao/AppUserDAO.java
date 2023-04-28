package org.travelling.ticketer.dao;

import org.travelling.ticketer.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserDAO extends JpaRepository<AppUser, Long> {

    boolean existsByUsername(String userName);
    AppUser findByUsername(String username);
}
