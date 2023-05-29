package org.travelling.ticketer.controllers;

import com.google.gson.Gson;
import org.travelling.ticketer.business.AppUserService;
import org.travelling.ticketer.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class AppUserController {

    private Gson gson;

    private AppUserService appUserService;

    @Autowired
    public AppUserController(Gson gson, AppUserService appUserService) {
        this.gson = gson;
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserCredentialsDTO userFromFrontend){
        UserPrivilegesDTO userPrivileges = appUserService.getUserPrivileges(userFromFrontend);
        if (userPrivileges == null){
            throw new IllegalArgumentException("User not found");
        }
        else{
            return gson.toJson(userPrivileges);
        }
    }

    @GetMapping("/checkUser/{username}")
    public boolean userExists(@PathVariable String username){
        return appUserService.userExists(username);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody AppUserDTO appUserDTO){
        appUserService.addUser(appUserDTO);
    }

    @PostMapping("/editUser/{userId}")
    public void editUser(@PathVariable long userId, @RequestBody UserPersonalDTO userPersonalDTO){
        appUserService.editUser(userId, userPersonalDTO);
    }

    @GetMapping("getUser/{userId}")
    public UserPersonalDTO getUserPersonalData(@PathVariable long userId){
        return appUserService.getUserPersonalData(userId);
    }

    @GetMapping("permissions/{userId}")
    public String getUserPermissions(@PathVariable long userId){
       return gson.toJson(appUserService.getUserPermissions(userId));
    }

    @GetMapping("users/roles")
    public String getUsersRoles(){
        return gson.toJson(appUserService.getUsersWithRoles());
    }

    @PostMapping("user/{username}/roles")
    public void editUserRoles(@PathVariable String username, @RequestBody RolesDTO roles){
        appUserService.editUserRoles(username, roles.getRoles());
    }


}
