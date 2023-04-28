package org.travelling.ticketer.mapper;

import org.travelling.ticketer.constants.DateTimeFormats;
import org.travelling.ticketer.dto.SeatsDTO;
import org.travelling.ticketer.entity.Seats;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class SeatsMapper {

    public SeatsDTO mapSeats(Seats seats){
        SeatsDTO seatsDTO = new SeatsDTO();
        seatsDTO.setFreeSeats(seats.getFreeSeats());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.TIME_FORMAT);
        seatsDTO.setTime(dateTimeFormatter.format(seats.getConnection().getTime()));
        seatsDTO.setFromStation(seats.getConnection().getFromStation());
        seatsDTO.setToStation(seats.getConnection().getToStation());
        seatsDTO.setId(seats.getConnection().getId());
        return seatsDTO;
    }

    public SeatsDTO mapSeats(LocalTime time, String from, String to, int freeSeats, long id){
        SeatsDTO seatsDTO = new SeatsDTO();
        seatsDTO.setFreeSeats(freeSeats);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormats.TIME_FORMAT);
        seatsDTO.setTime(dateTimeFormatter.format(time));
        seatsDTO.setFromStation(from);
        seatsDTO.setToStation(to);
        seatsDTO.setId(id);
        return seatsDTO;
    }

}
