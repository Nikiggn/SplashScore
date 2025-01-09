package com.splashScore.waterpolo_app.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "player_stats")
public class PlayerStat extends BaseEntity {

    @OneToOne
    private Player player;

    @Column
    private int goals;

    @Column
    private int assists;

    @Column
    private int shots;

    @Column(name = "shots_on_target")
    private int shotsOnTarget;

    @Column(name = "blocked_shots")
    private int blockedShots;

    @Column(name = "major_fouls")
    private int majorFouls;

    @Column(name = "matches_played")
    private int matchesPlayed;

    @Column(name = "yellow_cards")
    private int yellowCards;

    @Column(name = "red_cards")
    private int redCards;

    public PlayerStat() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public int getShotsOnTarget() {
        return shotsOnTarget;
    }

    public void setShotsOnTarget(int shotsOnTarget) {
        this.shotsOnTarget = shotsOnTarget;
    }

    public int getBlockedShots() {
        return blockedShots;
    }

    public void setBlockedShots(int blockedShots) {
        this.blockedShots = blockedShots;
    }

    public int getMajorFouls() {
        return majorFouls;
    }

    public void setMajorFouls(int majorFouls) {
        this.majorFouls = majorFouls;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }
}
