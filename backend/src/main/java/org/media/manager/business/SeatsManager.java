package org.media.manager.business;

import org.media.manager.dao.SeatsDAO;
import org.media.manager.dto.SeatsDTO;
import org.media.manager.entity.Connection;
import org.media.manager.entity.Seats;
import org.media.manager.entity.Train;
import org.media.manager.mapper.SeatsMapper;
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
