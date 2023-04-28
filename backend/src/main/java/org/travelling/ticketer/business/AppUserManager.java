package org.travelling.ticketer.business;

import org.travelling.ticketer.dao.AppUserDAO;
import org.travelling.ticketer.dto.AppUserDTO;
import org.travelling.ticketer.dto.UserCredentialsDTO;
import org.travelling.ticketer.dto.UserPersonalDTO;
import org.travelling.ticketer.dto.UserPrivilegesDTO;
import org.travelling.ticketer.entity.AppUser;
import org.travelling.ticketer.mapper.AppUserMapper;
import org.travelling.ticketer.utility.ExceptionBuilder;
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
