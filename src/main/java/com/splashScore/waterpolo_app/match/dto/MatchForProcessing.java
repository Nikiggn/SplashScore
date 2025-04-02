package com.splashScore.waterpolo_app.match.dto;


import com.splashScore.waterpolo_app.match.MatchStatus;

import java.util.UUID;


public class MatchForProcessing {
    private UUID id;
    private UUID homeClubId;
    private UUID awayClubId;
    private MatchStatus status;
    private int homeScore;
    private int awayScore;
    private boolean processed;

    public MatchForProcessing() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getHomeClubId() {
        return homeClubId;
    }

    public void setHomeClubId(UUID homeClubId) {
        this.homeClubId = homeClubId;
    }

    public UUID getAwayClubId() {
        return awayClubId;
    }

    public void setAwayClubId(UUID awayClubId) {
        this.awayClubId = awayClubId;
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

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
