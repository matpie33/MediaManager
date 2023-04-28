package org.media.manager.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.media.manager.constants.DateTimeFormats;
import org.media.manager.dao.*;
import org.media.manager.dto.*;
import org.media.manager.entity.*;
import org.media.manager.constants.TicketType;
import org.media.manager.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
public class ApplicationRestController {

    private final AppUserMapper appUserMapper;

    private final TicketMapper ticketMapper;

    private final TravelConnectionDAO travelConnectionDAO;

    private final AppUserDAO appUserDAO;

    private final TicketDao ticketDao;

    private Gson gson;

    private ConnectionMapper connectionMapper;

    private PasswordEncoder passwordEncoder;

    private SeatsDAO seatsDAO;

    private TrainDAO trainDAO;

    private SeatsMapper seatsMapper;

    private TrainMapper trainMapper;

    @Autowired
    public ApplicationRestController(TrainMapper trainMapper, SeatsMapper seatsMapper, AppUserMapper appUserMapper, TicketMapper ticketMapper, TravelConnectionDAO travelConnectionDAO, AppUserDAO appUserDAO, TicketDao ticketDao, ConnectionMapper connectionMapper, PasswordEncoder passwordEncoder, SeatsDAO seatsDAO, TrainDAO trainDAO) {
        this.appUserMapper = appUserMapper;
        this.ticketMapper = ticketMapper;
        this.travelConnectionDAO = travelConnectionDAO;
        this.appUserDAO = appUserDAO;
        this.ticketDao = ticketDao;
        this.connectionMapper = connectionMapper;
        this.passwordEncoder = passwordEncoder;
        this.seatsDAO = seatsDAO;
        this.trainDAO = trainDAO;
        this.seatsMapper = seatsMapper;
        this.trainMapper = trainMapper;
    }

    @PostConstruct
    public void initialize (){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    @GetMapping("/connection/{from}/to/{to}/sinceHour/{travelDateTime}")
    public String getConnectionsByStationAndTime(@PathVariable String from, @PathVariable String to, @PathVariable @DateTimeFormat(pattern = DateTimeFormats.DATE_TIME_FORMAT) LocalDateTime travelDateTime){
        Set<Connection> connections = travelConnectionDAO.findConnectionsByTimeGreaterThanEqualAndFromStationAndToStation(travelDateTime.toLocalTime(), from, to);
        return gson.toJson(connections.stream().map(connection -> getSeat(travelDateTime, connection, from, to)).collect(Collectors.toSet()));
    }


    private SeatsDTO getSeat(LocalDateTime travelDateTime, Connection connection, String from, String to) {
        Optional<Seats> seats = seatsDAO.findByConnection_IdAndFreeSeatsGreaterThanAndDate(connection.getId(), 0, travelDateTime.toLocalDate());
        if (seats.isPresent()){
            return seatsMapper.mapSeats(seats.get());
        }
        else{
            Train train = trainDAO.findById(connection.getTrain().getId()).orElseThrow(createIllegalArgumentException("Train not found"));
            return seatsMapper.mapSeats(connection.getTime(), from, to, train.getMaxSeats(), connection.getId());
        }
    }

    @Transactional
    @GetMapping("/assignTicket/{connectionId}/user/{userId}/ticket_type/{ticketType}/travelDate/{travelDateTime}")
    public boolean assignTicketToUser(@PathVariable long connectionId, @PathVariable long userId, @PathVariable String ticketType, @PathVariable @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime travelDateTime){

        Connection connection = travelConnectionDAO.findById(connectionId).orElseThrow(createIllegalArgumentException("Travel connection does not exist"));
        updateSeats(connection, travelDateTime, connectionId);
        assignTicketToUser(userId, ticketType, travelDateTime, connection);
        return true;

    }

    private void assignTicketToUser(long userId, String ticketType, LocalDateTime dateTime, Connection connection) {
        Ticket ticket = new Ticket();
        ticket.setTicketType(TicketType.fromString(ticketType));
        AppUser appUser = appUserDAO.findById(userId).orElseThrow(createIllegalArgumentException("User does not exist"));
        ticket.setConnection(connection);
        ticket.setAppUser(appUser);
        ticket.setTravelDate(dateTime.toLocalDate());
        ticketDao.save(ticket);
    }

    private void updateSeats (Connection connection, LocalDateTime travelDateTime, long connectionId){
        Optional<Seats> optionalSeats = seatsDAO.findByDateAndConnection_Id(travelDateTime.toLocalDate(), connectionId);
        Seats seats;
        if (optionalSeats.isPresent()){
            seats = optionalSeats.get();
            if (seats.getFreeSeats()<=0){
                throw new IllegalArgumentException("No seats available");
            }
            else{
                seats.decreaseFreeSeatAmount();
            }
        }
        else{
            seats = new Seats();
            seats.setDate(travelDateTime.toLocalDate());
            seats.setConnection(connection);
            seats.setFreeSeats(connection.getTrain().getMaxSeats() - 1);

        }
        seatsDAO.save(seats);
    }

    private Supplier<IllegalArgumentException> createIllegalArgumentException(String ex){
        return () -> new IllegalArgumentException(ex);
    }


    @GetMapping("/tickets/{userId}")
    public String getTicketsOfUser(@PathVariable long userId){
        Set<Ticket> tickets = ticketDao.findByAppUser_IdOrderByTravelDateAscConnection_timeAsc(userId);
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
    public String login(@RequestBody UserCredentialsDTO userFromFrontend){
        AppUser userFromDB = appUserDAO.findByUsername(userFromFrontend.getUserName());
        boolean isPasswordMatch = passwordEncoder.matches(userFromFrontend.getPassword(), userFromDB.getPassword());
        if (isPasswordMatch){
            return gson.toJson(appUserMapper.mapPrivileges(userFromDB));
        }
        throw new IllegalArgumentException("User not found");
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

    @PostMapping("connection/from/{from}/to/{to}/atTime/{time}/trainId/{trainId}")
    public void addConnection (@PathVariable String from, @PathVariable String to, @PathVariable String time,
                               @PathVariable long trainId ){
        Train train = trainDAO.findById(trainId).orElseThrow(createIllegalArgumentException("Train not found"));
        Connection connection = connectionMapper.mapConnection(from, to, time, train);
        travelConnectionDAO.save(connection);
    }

    @GetMapping("trains")
    public String getTrains (){
        List<Train> trains = trainDAO.findAll();
        return gson.toJson(trains.stream().map(trainMapper::mapTrain).collect(Collectors.toSet()));
    }


}
