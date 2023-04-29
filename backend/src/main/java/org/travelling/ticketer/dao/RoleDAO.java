package org.travelling.ticketer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.travelling.ticketer.constants.RoleType;
import org.travelling.ticketer.entity.Role;

import java.util.Set;

public interface RoleDAO extends JpaRepository<Role, Long> {
 Set<Role> findByRoleTypeIn(Set<RoleType> roles);
}
