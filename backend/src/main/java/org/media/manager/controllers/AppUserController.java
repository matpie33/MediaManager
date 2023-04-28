package org.media.manager.controllers;

import com.google.gson.Gson;
import org.media.manager.dao.AppUserDAO;
import org.media.manager.dto.AppUserDTO;
import org.media.manager.dto.UserCredentialsDTO;
import org.media.manager.dto.UserPersonalDTO;
import org.media.manager.entity.AppUser;
import org.media.manager.mapper.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppUserController {
    private final AppUserDAO appUserDAO;

    private final AppUserMapper appUserMapper;

    private PasswordEncoder passwordEncoder;

    private Gson gson;

    @Autowired
    public AppUserController(AppUserDAO appUserDAO, AppUserMapper appUserMapper, PasswordEncoder passwordEncoder, Gson gson) {
        this.appUserDAO = appUserDAO;
        this.appUserMapper = appUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.gson = gson;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserCredentialsDTO userFromFrontend){
        AppUser userFromDB = appUserDAO.findByUsername(userFromFrontend.getUserName());
        boolean isPasswordMatch = passwordEncoder.matches(userFromFrontend.getPassword(), userFromDB.getPassword());
        if (isPasswordMatch){
            return gson.toJson(appUserMapper.mapPrivileges(userFromDB));
        }
        throw new IllegalArgumentException("User not found");
    }

    @GetMapping("/checkUser/{username}")
    public boolean userExists(@PathVariable String username){
        return appUserDAO.existsByUsername(username);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody AppUserDTO appUserDTO){
        AppUser appUser = appUserMapper.mapUser(appUserDTO);
        appUserDAO.save(appUser);
    }

    @PostMapping("/editUser/{userId}")
    public void editUser(@PathVariable long userId, @RequestBody UserPersonalDTO userPersonalDTO){
        AppUser appUser = appUserDAO.findById(userId).orElseThrow(()->new IllegalArgumentException("user not found"));
        appUserMapper.mapUserPersonalData(appUser, userPersonalDTO);
        appUserDAO.save(appUser);
    }

    @GetMapping("getUser/{userId}")
    public UserPersonalDTO getUserPersonalData(@PathVariable long userId){
        AppUser appUser = appUserDAO.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return appUserMapper.getUserPersonalData(appUser);
    }

    @GetMapping("permissions/{userId}")
    public String getUserPermissions(@PathVariable long userId){
        AppUser appUser = appUserDAO.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return gson.toJson(appUserMapper.mapPrivileges(appUser));
    }


}
