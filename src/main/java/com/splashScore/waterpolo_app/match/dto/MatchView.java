package com.splashScore.waterpolo_app.match.dto;

import com.splashScore.waterpolo_app.club.model.Club;

import java.time.LocalDateTime;

public class MatchView {
    private Club homeTeam;
    private Club awayTeam;
    private LocalDateTime date;
    private String status;

    public MatchView(Club homeTeam, Club awayTeam, LocalDateTime date, String status) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.date = date;
        this.status = status;
    }

    public Club getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Club homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Club getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Club awayTeam) {
        this.awayTeam = awayTeam;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
