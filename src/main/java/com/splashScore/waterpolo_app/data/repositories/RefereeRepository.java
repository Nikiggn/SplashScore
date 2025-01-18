package com.splashScore.waterpolo_app.data.repositories;

import com.splashScore.waterpolo_app.data.entities.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Long> {
}
