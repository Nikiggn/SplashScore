package com.splashScore.waterpolo_app.match.repository;

import com.splashScore.waterpolo_app.match.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository  extends JpaRepository<Match, Long> {
}
