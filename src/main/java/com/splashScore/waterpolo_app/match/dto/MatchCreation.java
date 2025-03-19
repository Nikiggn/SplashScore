package com.splashScore.waterpolo_app.match.dto;

import com.splashScore.waterpolo_app.match.MatchStatus;

import java.time.LocalDateTime;

public class MatchCreation {
    private Long homeTeamId;
    private Long awayTeamId;
    private LocalDateTime date;
    private MatchStatus status;
    private int homeScore;  // Nullable for non-completed matches
    private int awayScore;  // Nullable for non-completed matches


    public MatchCreation() {
    }

    public MatchCreation(Long homeTeam, Long awayTeam, LocalDateTime date, MatchStatus status, int homeScore, int awayScore) {
        this.homeTeamId = homeTeam;
        this.awayTeamId = awayTeam;
        this.date = date;
        this.status = status;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Long awayTeamId) {
        this.awayTeamId = awayTeamId;
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
}
