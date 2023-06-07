package org.travelling.ticketer.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.travelling.ticketer.constants.RoleType;
import org.travelling.ticketer.dao.AppUserDAO;
import org.travelling.ticketer.dao.DelayDAO;
import org.travelling.ticketer.dao.RoleDAO;
import org.travelling.ticketer.dao.TravelConnectionDAO;
import org.travelling.ticketer.dto.*;
import org.travelling.ticketer.entity.AppUser;
import org.travelling.ticketer.entity.Connection;
import org.travelling.ticketer.entity.Delay;
import org.travelling.ticketer.entity.Role;
import org.travelling.ticketer.mapper.AppUserMapper;
import org.travelling.ticketer.utility.ExceptionBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DelayService {

    private DelayDAO delayDAO;

    private TravelConnectionDAO travelConnectionDAO;

    @Autowired
    public DelayService(DelayDAO delayDAO, TravelConnectionDAO travelConnectionDAO) {
        this.delayDAO = delayDAO;
        this.travelConnectionDAO = travelConnectionDAO;
    }

    public void addDelay (int delayValue, LocalDate date, long connectionId){
        Delay delay = new Delay();
        Connection connection = travelConnectionDAO.findById(connectionId).orElseThrow(ExceptionBuilder.createIllegalArgumentException("Connection with id: " + connectionId + " not found."));
        delay.setConnection(connection);
        delay.setDelayMinutes(delayValue);
        delay.setDate(date);
        delayDAO.save(delay);
    }


}
