package org.media.manager.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.media.manager.dao.ConnectionDAO;
import org.media.manager.entity.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.Set;

@RestController
public class TicketsController {

    @Autowired
    private ConnectionDAO connectionDAO;

    @GetMapping("/connection/{from}/to/{to}/sinceHour/{time}")
    public String example(@PathVariable String from, @PathVariable String to, @PathVariable Time time){
        Set<Connection> connections = connectionDAO.findConnectionsByTimeGreaterThanEqualAndFromStationAndToStation(time, from, to);
        System.out.println(connections);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(connections) ;
    }

}
