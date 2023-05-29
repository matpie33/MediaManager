package org.travelling.ticketer.dao;

import org.springframework.stereotype.Repository;
import org.travelling.ticketer.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TrainDAO extends JpaRepository<Train, Long> {


}
