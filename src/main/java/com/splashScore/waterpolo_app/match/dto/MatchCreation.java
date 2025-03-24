package com.splashScore.waterpolo_app.match.dto;

import com.splashScore.waterpolo_app.match.MatchStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class MatchCreation {
    private UUID homeTeamId;
    private UUID awayTeamId;
    private LocalDateTime date;
    private MatchStatus status;
    private int homeScore;
    private int awayScore;


    public MatchCreation() {
    }

    public MatchCreation(UUID homeTeam, UUID awayTeam, LocalDateTime date, MatchStatus status, int homeScore, int awayScore) {
        this.homeTeamId = homeTeam;
        this.awayTeamId = awayTeam;
        this.date = date;
        this.status = status;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
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

    public UUID getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(UUID homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public UUID getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(UUID awayTeamId) {
        this.awayTeamId = awayTeamId;
    }
}
