package com.splashScore.waterpolo_app.data.entities;

import com.splashScore.waterpolo_app.data.entities.enums.MatchStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Set;
import java.sql.Time;
import java.time.Instant;
import java.util.HashSet;

@Entity
@Table(name = "matches")
public class Match extends BaseEntity{

    private LocalDateTime dateTime;

    @ManyToOne
    private Pool pool;

    //private Club homeClub;

    //private Club awayClub;

    private int homeScore;

    private int awayScore;

    private MatchStatus status;

     //private Set<Referee> referees;

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

//    public Club getHomeClub() {
//        return homeClub;
//    }
//
//    public void setHomeClub(Club homeClub) {
//        this.homeClub = homeClub;
//    }
//
//    public Club getAwayClub() {
//        return awayClub;
//    }
//
//    public void setAwayClub(Club awayClub) {
//        this.awayClub = awayClub;
//    }

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

//    public Set<Referee> getReferees() {
//        return referees;
//    }
//
//    public void setReferees(Set<Referee> referees) {
//        this.referees = referees;
//    }
}
