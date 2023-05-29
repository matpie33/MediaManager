package org.travelling.ticketer.business;

import org.springframework.stereotype.Service;
import org.travelling.ticketer.dao.SeatsDAO;
import org.travelling.ticketer.dto.SeatsDTO;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Seats;
import org.travelling.ticketer.entity.Train;
import org.travelling.ticketer.mapper.SeatsMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SeatsService {

    private final SeatsDAO seatsDAO;

    private final SeatsMapper seatsMapper;

    private final TrainsService trainsService;

    @Autowired
    public SeatsService(SeatsDAO seatsDAO, SeatsMapper seatsMapper, TrainsService trainsService) {
        this.seatsDAO = seatsDAO;
        this.seatsMapper = seatsMapper;
        this.trainsService = trainsService;
    }

    public SeatsDTO getSeat(LocalDateTime travelDateTime, Connection connection, String from, String to) {
        Optional<Seats> seats = seatsDAO.findByConnection_IdAndDate(connection.getId(), travelDateTime.toLocalDate());
        if (seats.isPresent()){
            if (seats.get().getFreeSeats()>0){
                return seatsMapper.mapSeats(seats.get());
            }
            else{
                return null;
            }
        }
        else{
            Train train = trainsService.getTrainByConnection(connection);
            return seatsMapper.mapSeats(connection.getDepartureTime(), from, to, train.getMaxSeats(), connection.getId());
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
