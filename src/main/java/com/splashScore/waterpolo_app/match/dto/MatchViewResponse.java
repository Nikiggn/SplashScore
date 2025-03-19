package com.splashScore.waterpolo_app.match.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MatchViewResponse {
     private Long homeTeam;
     private Long awayTeam;
     private LocalDateTime date;
     private String status;

    public MatchViewResponse() {
    }

    public Long getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Long homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Long getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Long awayTeam) {
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
