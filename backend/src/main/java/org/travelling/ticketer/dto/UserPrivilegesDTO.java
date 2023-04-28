package org.travelling.ticketer.dto;

import org.travelling.ticketer.constants.PermissionType;

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
