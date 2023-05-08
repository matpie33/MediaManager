package org.travelling.ticketer.controllers;

import com.google.gson.Gson;
import org.travelling.ticketer.business.AppUserManager;
import org.travelling.ticketer.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("users/roles")
    public String getUsersRoles(){
        return gson.toJson(appUserManager.getUsersWithRoles());
    }

    @PostMapping("user/{username}/roles")
    public void editUserRoles(@PathVariable String username, @RequestBody RolesDTO roles){
        appUserManager.editUserRoles(username, roles.getRoles());
    }


}
