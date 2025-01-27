package com.splashScore.waterpolo_app.referee.repository;

import com.splashScore.waterpolo_app.referee.model.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Long> {
}
