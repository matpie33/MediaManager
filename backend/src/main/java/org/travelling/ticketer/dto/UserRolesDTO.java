package org.travelling.ticketer.dto;

import org.travelling.ticketer.constants.RoleType;

import java.util.Set;

public class UserRolesDTO {

    private String userName;
    private Set<RoleType> roles;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoles(Set<RoleType> roles) {
        this.roles = roles;
    }
}
