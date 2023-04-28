package org.travelling.ticketer.mapper;

import org.travelling.ticketer.dto.TrainDTO;
import org.travelling.ticketer.entity.Train;
import org.springframework.stereotype.Component;

@Component
public class TrainMapper {

    public TrainDTO mapTrain(Train train){
        TrainDTO trainDTO = new TrainDTO();
        trainDTO.setId(train.getId());
        trainDTO.setName(train.getName());
        return trainDTO;
    }

}
