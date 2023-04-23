package org.media.manager.entity;

import org.media.manager.constants.PermissionType;

import javax.persistence.*;

@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;

    public PermissionType getPermissionType() {
        return permissionType;
    }
}
