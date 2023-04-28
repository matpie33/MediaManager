package org.travelling.ticketer.entity;

import org.travelling.ticketer.constants.RoleType;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToMany
    private Set<Permission> permissions;

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
