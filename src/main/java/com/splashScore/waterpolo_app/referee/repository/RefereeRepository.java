package com.splashScore.waterpolo_app.referee.repository;

import com.splashScore.waterpolo_app.referee.model.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, UUID> {
    Optional<Referee> findByFullName(String fullName);
}
