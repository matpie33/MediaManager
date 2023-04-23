package org.media.manager.mapper;

import org.media.manager.dto.AppUserDTO;
import org.media.manager.dto.UserPersonalDTO;
import org.media.manager.dto.UserPrivilegesDTO;
import org.media.manager.entity.AppUser;
import org.media.manager.entity.Permission;
import org.media.manager.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class AppUserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserMapper (PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser mapUser(AppUserDTO appUserDTO){
        AppUser appUser = new AppUser();
        appUser.setUsername(appUserDTO.getUserCredentials().getUserName());
        appUser.setPassword(passwordEncoder.encode(appUserDTO.getUserCredentials().getPassword()));
        appUser.setFirstName(appUserDTO.getPersonalData().getFirstName());
        appUser.setLastName(appUserDTO.getPersonalData().getLastName());
        appUser.setEmail(appUserDTO.getPersonalData().getEmail());
        return appUser;
    }

    public UserPersonalDTO getUserPersonalData(AppUser appUser){
        UserPersonalDTO userPersonalDTO = new UserPersonalDTO();
        userPersonalDTO.setEmail(appUser.getEmail());
        userPersonalDTO.setFirstName(appUser.getFirstName());
        userPersonalDTO.setLastName(appUser.getLastName());
        return userPersonalDTO;
    }

    public void mapUserPersonalData(AppUser appUser, UserPersonalDTO userPersonalDTO) {
        appUser.setEmail(userPersonalDTO.getEmail());
        appUser.setFirstName(userPersonalDTO.getFirstName());
        appUser.setLastName(userPersonalDTO.getLastName());
    }

    public UserPrivilegesDTO mapPrivileges (AppUser appUser){
        UserPrivilegesDTO userPrivilegesDTO = new UserPrivilegesDTO();
        userPrivilegesDTO.setPermissions(appUser.getRoles().stream().map(Role::getPermissions).flatMap(Collection::stream).map(Permission::getPermissionType).collect(Collectors.toSet()));
        userPrivilegesDTO.setId(appUser.getId());
        return userPrivilegesDTO;
    }

}
