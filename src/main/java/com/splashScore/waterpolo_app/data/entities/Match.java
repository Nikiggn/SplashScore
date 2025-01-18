package com.splashScore.waterpolo_app.data.entities;

import com.splashScore.waterpolo_app.data.entities.enums.MatchStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.sql.Time;
import java.time.Instant;
import java.util.HashSet;

@Entity
@Table(name = "matches")
public class Match extends BaseEntity {

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "pool_id")
    private Pool pool;

    @ManyToOne
    @JoinColumn(name = "home_club_id") // Foreign key column in Match table
    private Club homeClub;

    @ManyToOne
    @JoinColumn(name = "away_club_id")
    private Club awayClub;

    private int homeScore;

    private int awayScore;

    private MatchStatus status;

    @ManyToMany
    @JoinTable(
            name = "match_referee", // Join table name
            joinColumns = @JoinColumn(name = "match_id"), // Foreign key for Match
            inverseJoinColumns = @JoinColumn(name = "referee_id") // Foreign key for Referee
    )
    private Set<Referee> referees;

    // private MatchRound matchRound

    // stats


    public Match() {
        //referees = new HashSet<>();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }


    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public Club getHomeClub() {
        return homeClub;
    }

    public void setHomeClub(Club homeClub) {
        this.homeClub = homeClub;
    }

    public Club getAwayClub() {
        return awayClub;
    }

    public void setAwayClub(Club awayClub) {
        this.awayClub = awayClub;
    }

    public Set<Referee> getReferees() {
        return referees;
    }

    public void setReferees(Set<Referee> referees) {
        this.referees = referees;
    }
}