package org.media.manager.business;

import org.media.manager.dao.AppUserDAO;
import org.media.manager.dto.AppUserDTO;
import org.media.manager.dto.UserCredentialsDTO;
import org.media.manager.dto.UserPersonalDTO;
import org.media.manager.dto.UserPrivilegesDTO;
import org.media.manager.entity.AppUser;
import org.media.manager.mapper.AppUserMapper;
import org.media.manager.utility.ExceptionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppUserManager {

    private final AppUserDAO appUserDAO;

    private final AppUserMapper appUserMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserManager(AppUserDAO appUserDAO, AppUserMapper appUserMapper, PasswordEncoder passwordEncoder) {
        this.appUserDAO = appUserDAO;
        this.appUserMapper = appUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserPrivilegesDTO getUserPrivileges(UserCredentialsDTO userFromFrontend){
        AppUser userFromDB = appUserDAO.findByUsername(userFromFrontend.getUserName());
        boolean isPasswordMatch = passwordEncoder.matches(userFromFrontend.getPassword(), userFromDB.getPassword());
        if (isPasswordMatch){
            return appUserMapper.mapPrivileges(userFromDB);
        }
        return null;
    }

    public boolean userExists (String username){
        return appUserDAO.existsByUsername(username);
    }

    public void addUser(AppUserDTO appUserDTO){
        AppUser appUser = appUserMapper.mapUser(appUserDTO);
        appUserDAO.save(appUser);
    }

    public void editUser (long userId, UserPersonalDTO userPersonalDTO){
        AppUser appUser = appUserDAO.findById(userId).orElseThrow(()->new IllegalArgumentException("user not found"));
        appUserMapper.mapUserPersonalData(appUser, userPersonalDTO);
        appUserDAO.save(appUser);
    }

    public UserPersonalDTO getUserPersonalData(long userId){
        AppUser appUser = appUserDAO.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return appUserMapper.getUserPersonalData(appUser);
    }

    public UserPrivilegesDTO getUserPermissions(long userId){
        AppUser appUser = appUserDAO.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return appUserMapper.mapPrivileges(appUser);
    }

    public AppUser getUserById (long userId){
       return  appUserDAO.findById(userId).orElseThrow(ExceptionBuilder.createIllegalArgumentException("User does not exist"));
    }


}
