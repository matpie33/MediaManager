package org.travelling.ticketer.dao;

import org.travelling.ticketer.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserDAO extends JpaRepository<AppUser, Long> {

    boolean existsByUsername(String userName);
    Optional<AppUser> findByUsername(String username);
}
