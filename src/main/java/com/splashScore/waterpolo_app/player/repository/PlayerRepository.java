package com.splashScore.waterpolo_app.player.repository;

import com.splashScore.waterpolo_app.player.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByClubId(Long id);
 }
