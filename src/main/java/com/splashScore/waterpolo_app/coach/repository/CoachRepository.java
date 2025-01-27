package com.splashScore.waterpolo_app.coach.repository;

import com.splashScore.waterpolo_app.coach.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
}
