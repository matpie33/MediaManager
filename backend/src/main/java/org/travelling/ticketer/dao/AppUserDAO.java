package org.travelling.ticketer.dao;

import org.springframework.stereotype.Repository;
import org.travelling.ticketer.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface AppUserDAO extends JpaRepository<AppUser, Long> {

    boolean existsByUsername(String userName);
    Optional<AppUser> findByUsername(String username);
}
