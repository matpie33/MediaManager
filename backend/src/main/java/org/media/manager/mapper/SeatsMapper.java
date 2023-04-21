package org.media.manager.mapper;

import org.media.manager.controllers.ApplicationRestController;
import org.media.manager.dto.SeatDTO;
import org.media.manager.entity.Seats;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class SeatsMapper {

    public static final String TIME_FORMAT = "HH:mm";

    public SeatDTO mapSeats(Seats seats){
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setFreeSeats(seats.getFreeSeats());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        seatDTO.setTime(dateTimeFormatter.format(seats.getConnection().getTime()));
        seatDTO.setFromStation(seats.getConnection().getFromStation());
        seatDTO.setToStation(seats.getConnection().getToStation());
        seatDTO.setId(seats.getConnection().getId());
        return seatDTO;
    }

    public SeatDTO mapSeats(LocalTime time, String from, String to, int freeSeats, long id){
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setFreeSeats(freeSeats);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        seatDTO.setTime(dateTimeFormatter.format(time));
        seatDTO.setFromStation(from);
        seatDTO.setToStation(to);
        seatDTO.setId(id);
        return seatDTO;
    }

}
