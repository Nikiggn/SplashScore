package com.splashScore.waterpolo_app.player.repository;

import com.splashScore.waterpolo_app.player.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    List<Player> findByClubId(UUID id);
 }
