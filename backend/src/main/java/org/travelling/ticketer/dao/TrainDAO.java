package org.travelling.ticketer.dao;

import org.travelling.ticketer.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainDAO extends JpaRepository<Train, Long> {


}
