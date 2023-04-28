package org.media.manager.business;

import org.media.manager.dao.TrainDAO;
import org.media.manager.dto.TrainDTO;
import org.media.manager.entity.Connection;
import org.media.manager.entity.Train;
import org.media.manager.mapper.TrainMapper;
import org.media.manager.utility.ExceptionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TrainsManager {

    private TrainDAO trainDAO;

    private TrainMapper trainMapper;

    @Autowired
    public TrainsManager(TrainDAO trainDAO, TrainMapper trainMapper) {
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
