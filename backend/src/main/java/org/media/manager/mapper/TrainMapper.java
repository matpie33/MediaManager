package org.media.manager.mapper;

import org.media.manager.dto.TrainDTO;
import org.media.manager.entity.Train;
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
