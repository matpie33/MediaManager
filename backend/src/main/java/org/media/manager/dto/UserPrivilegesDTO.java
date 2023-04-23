package org.media.manager.dto;

import org.media.manager.constants.PermissionType;

import java.util.HashSet;
import java.util.Set;

public class UserPrivilegesDTO {

    private long id;

    private Set<PermissionType> permissions = new HashSet<>();

    public void setPermissions(Set<PermissionType> permissions) {
        this.permissions = permissions;
    }

    public void setId(long id) {
        this.id = id;
    }
}
