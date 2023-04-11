package org.media.manager.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.media.manager.dao.AppUserDAO;
import org.media.manager.dao.TravelConnectionDAO;
import org.media.manager.dto.AppUserDTO;
import org.media.manager.entity.AppUser;
import org.media.manager.entity.Connection;
import org.media.manager.mapper.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Set;

@RestController
public class ApplicationRestController {

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private TravelConnectionDAO travelConnectionDAO;

    @Autowired
    private AppUserDAO appUserDAO;

    @GetMapping("/connection/{from}/to/{to}/sinceHour/{time}")
    public String example(@PathVariable String from, @PathVariable String to, @PathVariable Time time){
        Set<Connection> connections = travelConnectionDAO.findConnectionsByTimeGreaterThanEqualAndFromStationAndToStation(time, from, to);
        System.out.println(connections);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(connections) ;
    }

    @GetMapping("/checkUser/{username}")
    public boolean userExists(@PathVariable String username){
        return appUserDAO.existsByUsername(username);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody AppUserDTO appUserDTO){
        AppUser appUser = appUserMapper.mapUserDTO(appUserDTO);
        appUserDAO.save(appUser);
    }

}
