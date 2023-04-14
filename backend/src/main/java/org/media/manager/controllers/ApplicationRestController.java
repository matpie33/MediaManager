package org.media.manager.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.media.manager.dao.AppUserDAO;
import org.media.manager.dao.TicketDao;
import org.media.manager.dao.TravelConnectionDAO;
import org.media.manager.dto.AppUserDTO;
import org.media.manager.dto.TicketDTO;
import org.media.manager.dto.UserCredentialsDTO;
import org.media.manager.dto.UserPersonalDTO;
import org.media.manager.entity.AppUser;
import org.media.manager.entity.Connection;
import org.media.manager.entity.Ticket;
import org.media.manager.enums.TicketType;
import org.media.manager.mapper.AppUserMapper;
import org.media.manager.mapper.ConnectionMapper;
import org.media.manager.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins= "http://localhost:4200")
public class ApplicationRestController {

    private final AppUserMapper appUserMapper;

    private final TicketMapper ticketMapper;

    private final TravelConnectionDAO travelConnectionDAO;

    private final AppUserDAO appUserDAO;

    private final TicketDao ticketDao;

    private Gson gson;

    private ConnectionMapper connectionMapper;

    private PasswordEncoder passwordEncoder;



    @Autowired
    public ApplicationRestController(AppUserMapper appUserMapper, TicketMapper ticketMapper, TravelConnectionDAO travelConnectionDAO, AppUserDAO appUserDAO, TicketDao ticketDao, ConnectionMapper connectionMapper, PasswordEncoder passwordEncoder) {
        this.appUserMapper = appUserMapper;
        this.ticketMapper = ticketMapper;
        this.travelConnectionDAO = travelConnectionDAO;
        this.appUserDAO = appUserDAO;
        this.ticketDao = ticketDao;
        this.connectionMapper = connectionMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initialize (){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    @GetMapping("/connection/{from}/to/{to}/sinceHour/{time}")
    public String getConnectionsByStationAndTime(@PathVariable String from, @PathVariable String to, @PathVariable Time time){
        Set<Connection> connections = travelConnectionDAO.findConnectionsByTimeGreaterThanEqualAndFromStationAndToStation(time, from, to);
        return gson.toJson(connections.stream().map(connectionMapper::mapConnection).collect(Collectors.toSet()));
    }

    @GetMapping("/assignTicket/{connectionId}/user/{userId}/ticket_type/{ticketType}/travelDate/{travelDate}")
    public boolean assignTicketToUser(@PathVariable long connectionId, @PathVariable long userId, @PathVariable String ticketType, @PathVariable Date travelDate){
        Ticket ticket = new Ticket();
        ticket.setTicketType(TicketType.fromString(ticketType));
        Connection connection = travelConnectionDAO.findById(connectionId).orElseThrow(() -> new IllegalArgumentException("Travel connection does not exist"));
        AppUser appUser = appUserDAO.findById(userId).orElseThrow(() -> new IllegalArgumentException("User does not exist"));
        ticket.setConnection(connection);
        ticket.setAppUser(appUser);
        ticket.setTravelDate(travelDate);
        ticketDao.save(ticket);
        return true;

    }

    @GetMapping("/tickets/{userId}")
    public String getTicketsOfUser(@PathVariable long userId){
        Set<Ticket> tickets = ticketDao.findByAppUser_IdOrderByTravelDateAsc(userId);
        LinkedHashSet<TicketDTO> set = tickets.stream().map(ticketMapper::mapTicket).collect(Collectors.toCollection(LinkedHashSet::new));
        return gson.toJson(set);

    }

    @GetMapping("/checkUser/{username}")
    public boolean userExists(@PathVariable String username){
        return appUserDAO.existsByUsername(username);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<String> handlePreconditionFailed(DataIntegrityViolationException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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

    @PostMapping("/login")
    public long login(@RequestBody UserCredentialsDTO userFromFrontend){
        AppUser userFromDB = appUserDAO.findByUsername(userFromFrontend.getUserName());
        boolean isPasswordMatch = passwordEncoder.matches(userFromFrontend.getPassword(), userFromDB.getPassword());
        if (isPasswordMatch){
            return userFromDB.getId();
        }
        throw new IllegalArgumentException("User not found");
    }

}
