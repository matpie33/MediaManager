package org.travelling.ticketer.business;

import org.springframework.stereotype.Service;
import org.travelling.ticketer.dao.TrainDAO;
import org.travelling.ticketer.dto.TrainDTO;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Train;
import org.travelling.ticketer.mapper.TrainMapper;
import org.travelling.ticketer.utility.ExceptionBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrainsService {

    private TrainDAO trainDAO;

    private TrainMapper trainMapper;

    @Autowired
    public TrainsService(TrainDAO trainDAO, TrainMapper trainMapper) {
        this.trainDAO = trainDAO;
        this.trainMapper = trainMapper;
    }

    public Train getTrainByConnection(Connection connection){
        return trainDAO.findById(connection.getTrain().getId()).orElseThrow(ExceptionBuilder.createIllegalArgumentException("Train not found"));
    }

    public Train getTrainById(long trainId){
        return trainDAO.findById(trainId).orElseThrow(ExceptionBuilder.createIllegalArgumentException("Train not found"));
    }


    public Set<TrainDTO> getTrainsInformation(){
        List<Train> trains = trainDAO.findAll();
        return trains.stream().map(trainMapper::mapTrain).collect(Collectors.toSet());
    }


}
