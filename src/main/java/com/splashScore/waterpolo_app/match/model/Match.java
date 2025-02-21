package com.splashScore.waterpolo_app.match.model;

 import com.splashScore.waterpolo_app.club.model.Club;
 import com.splashScore.waterpolo_app.referee.model.Referee;
 import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "home_club_id") // Foreign key column in Match table
    private Club homeClub;

    @ManyToOne
    @JoinColumn(name = "away_club_id")
    private Club awayClub;

    private int homeScore;

    private int awayScore;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}