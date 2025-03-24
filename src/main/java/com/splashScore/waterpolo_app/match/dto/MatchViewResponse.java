package com.splashScore.waterpolo_app.match.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class MatchViewResponse {
     private UUID homeTeam;
     private UUID awayTeam;
     private LocalDateTime date;
     private String status;

    public MatchViewResponse() {
    }

    public UUID getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(UUID homeTeam) {
        this.homeTeam = homeTeam;
    }

    public UUID getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(UUID awayTeam) {
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
