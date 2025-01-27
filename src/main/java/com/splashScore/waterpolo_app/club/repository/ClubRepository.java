package com.splashScore.waterpolo_app.club.repository;

import com.splashScore.waterpolo_app.club.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
}
