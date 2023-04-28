package org.media.manager.controllers;

import com.google.gson.Gson;
import org.media.manager.business.AppUserManager;
import org.media.manager.dao.AppUserDAO;
import org.media.manager.dto.AppUserDTO;
import org.media.manager.dto.UserCredentialsDTO;
import org.media.manager.dto.UserPersonalDTO;
import org.media.manager.dto.UserPrivilegesDTO;
import org.media.manager.mapper.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppUserController {

    private Gson gson;

    private AppUserManager appUserManager;

    @Autowired
    public AppUserController(Gson gson, AppUserManager appUserManager) {
        this.gson = gson;
        this.appUserManager = appUserManager;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserCredentialsDTO userFromFrontend){
        UserPrivilegesDTO userPrivileges = appUserManager.getUserPrivileges(userFromFrontend);
        if (userPrivileges == null){
            throw new IllegalArgumentException("User not found");
        }
        else{
            return gson.toJson(userPrivileges);
        }
    }

    @GetMapping("/checkUser/{username}")
    public boolean userExists(@PathVariable String username){
        return appUserManager.userExists(username);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody AppUserDTO appUserDTO){
        appUserManager.addUser(appUserDTO);
    }

    @PostMapping("/editUser/{userId}")
    public void editUser(@PathVariable long userId, @RequestBody UserPersonalDTO userPersonalDTO){
        appUserManager.editUser(userId, userPersonalDTO);
    }

    @GetMapping("getUser/{userId}")
    public UserPersonalDTO getUserPersonalData(@PathVariable long userId){
        return appUserManager.getUserPersonalData(userId);
    }

    @GetMapping("permissions/{userId}")
    public String getUserPermissions(@PathVariable long userId){
       return gson.toJson(appUserManager.getUserPermissions(userId));
    }


}
