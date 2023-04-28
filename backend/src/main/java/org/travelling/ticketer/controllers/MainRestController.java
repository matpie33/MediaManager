package org.travelling.ticketer.controllers;


import com.google.gson.Gson;
import org.travelling.ticketer.business.SeatsManager;
import org.travelling.ticketer.business.TicketsManager;
import org.travelling.ticketer.business.TrainsManager;
import org.travelling.ticketer.business.TravelConnectionManager;
import org.travelling.ticketer.constants.DateTimeFormats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Train;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
public class MainRestController {

    private final Gson gson;

    private final SeatsManager seatsManager;

    private final TrainsManager trainsManager;

    private final TravelConnectionManager travelConnectionManager;

    private final TicketsManager ticketsManager;

    @Autowired
    public MainRestController(Gson gson, SeatsManager seatsManager, TrainsManager trainsManager, TravelConnectionManager travelConnectionManager, TicketsManager ticketsManager) {
        this.gson = gson;
        this.seatsManager = seatsManager;
        this.trainsManager = trainsManager;
        this.travelConnectionManager = travelConnectionManager;
        this.ticketsManager = ticketsManager;
    }

    @GetMapping("/connection/{from}/to/{to}/sinceHour/{travelDateTime}")
    public String getConnectionsByStationAndTime(@PathVariable String from, @PathVariable String to, @PathVariable @DateTimeFormat(pattern = DateTimeFormats.DATE_TIME_FORMAT) LocalDateTime travelDateTime){
        return gson.toJson(travelConnectionManager.getConnectionsWithFreeSeats(from,to, travelDateTime));
    }

    @Transactional
    @GetMapping("/assignTicket/{connectionId}/user/{userId}/ticket_type/{ticketType}/travelDate/{travelDateTime}")
    public void assignTicketToUser(@PathVariable long connectionId, @PathVariable long userId, @PathVariable String ticketType, @PathVariable @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime travelDateTime){

        Connection connection = travelConnectionManager.getConnectionById(connectionId);
        seatsManager.updateSeats(connection, travelDateTime, connectionId);
        ticketsManager.assignTicketToUser(userId, ticketType,travelDateTime, connection);
    }

    @GetMapping("/tickets/{userId}")
    public String getTicketsOfUser(@PathVariable long userId){
        return gson.toJson(ticketsManager.getTicketsOfUser(userId));
    }

    @PostMapping("connection/from/{from}/to/{to}/atTime/{time}/trainId/{trainId}")
    public void addConnection (@PathVariable String from, @PathVariable String to, @PathVariable String time,
                               @PathVariable long trainId ){
        Train train = trainsManager.getTrainById(trainId);
        travelConnectionManager.addNewConnection(from, to, time, train);
    }

    @GetMapping("trains")
    public String getTrains (){
        return gson.toJson(trainsManager.getTrainsInformation());
    }


}
