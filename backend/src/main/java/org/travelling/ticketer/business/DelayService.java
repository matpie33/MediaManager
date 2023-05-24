package org.travelling.ticketer.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.travelling.ticketer.dao.DelayDAO;
import org.travelling.ticketer.entity.Delay;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DelayService {

    private final DelayDAO delayDAO;

    @Autowired
    public DelayService(DelayDAO delayDAO) {
        this.delayDAO = delayDAO;
    }

    public long getDelay(long connectionId){
        LocalDate today = LocalDate.now();
        Optional<Delay> delay = delayDAO.findByConnection_idAndDate(connectionId, today);
        return delay.map(Delay::getDelayMinutes).orElse(0);

    }

}
