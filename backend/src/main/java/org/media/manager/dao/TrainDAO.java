package org.media.manager.dao;

import org.media.manager.entity.AppUser;
import org.media.manager.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainDAO extends JpaRepository<Train, Long> {


}
