package org.media.manager.entity;

import org.media.manager.constants.RoleType;

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
