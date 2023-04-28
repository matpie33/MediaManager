package org.travelling.ticketer.business;

import org.travelling.ticketer.dao.SeatsDAO;
import org.travelling.ticketer.dto.SeatsDTO;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Seats;
import org.travelling.ticketer.entity.Train;
import org.travelling.ticketer.mapper.SeatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class SeatsManager {

    private SeatsDAO seatsDAO;

    private SeatsMapper seatsMapper;

    private TrainsManager trainsManager;

    @Autowired
    public SeatsManager(SeatsDAO seatsDAO, SeatsMapper seatsMapper, TrainsManager trainsManager) {
        this.seatsDAO = seatsDAO;
        this.seatsMapper = seatsMapper;
        this.trainsManager = trainsManager;
    }

    public SeatsDTO getSeat(LocalDateTime travelDateTime, Connection connection, String from, String to) {
        Optional<Seats> seats = seatsDAO.findByConnection_IdAndAndDate(connection.getId(), travelDateTime.toLocalDate());
        if (seats.isPresent()){
            if (seats.get().getFreeSeats()>0){
                return seatsMapper.mapSeats(seats.get());
            }
            else{
                return null;
            }
        }
        else{
            Train train = trainsManager.getTrainByConnection(connection);
            return seatsMapper.mapSeats(connection.getTime(), from, to, train.getMaxSeats(), connection.getId());
        }
    }

    public void updateSeats (Connection connection, LocalDateTime travelDateTime, long connectionId){
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

}
