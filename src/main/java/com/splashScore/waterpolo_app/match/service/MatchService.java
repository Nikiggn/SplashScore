package com.splashScore.waterpolo_app.match.service;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.exception.MatchCreationException;
import com.splashScore.waterpolo_app.match.client.MatchClient;
import com.splashScore.waterpolo_app.match.MatchStatus;
import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.dto.MatchView;
import com.splashScore.waterpolo_app.player.model.Player;
import com.splashScore.waterpolo_app.player.model.Status;
import com.splashScore.waterpolo_app.referee.model.Referee;
import com.splashScore.waterpolo_app.referee.service.RefereeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class MatchService {
    private final MatchClient matchClient;
    private final ClubService clubService;
    private final RefereeService refereeService;

    @Autowired
    public MatchService(MatchClient matchClient, ClubService clubService, RefereeService refereeService) {
        this.matchClient = matchClient;
        this.clubService = clubService;
        this.refereeService = refereeService;
    }

    public List<MatchView> getAllMatchesWithClubDetails() {
        List<MatchCreation> matches = matchClient.getAllMatches().getBody();

        assert matches != null;

        return matches.stream().map(match -> {
            Club homeClub = clubService.getClubById(match.getHomeTeamId());
            Club awayClub = clubService.getClubById(match.getAwayTeamId());
            return new MatchView(homeClub, awayClub, match.getDate(), match.getStatus().toString());
        }).toList();
    }

    public void checkAllCriteriaForCreatingMatch(){
        if (clubService.getAllClubs().size() <= 1 || refereeService.getAllActiveReferees().isEmpty()) {
            throw new MatchCreationException("There are no matches to be created");
        }
    }

    public List<MatchCreation> getMatchesByPage(int page, int size) {
        return matchClient.getMatchesByPage(page, size); // Fetch 5 matches per page
    }

    public void addClubAndRefereeDetailsToEachMatch(List<MatchCreation> matches) {
        for (MatchCreation match : matches) {
            Club homeTeam = clubService.getClubById(match.getHomeTeamId());
            Club awayTeam = clubService.getClubById(match.getAwayTeamId());
            Referee referee = refereeService.getRefereeById(match.getRefereeId());

            match.setHomeClubName(homeTeam.getName());
            match.setAwayClubName(awayTeam.getName());
            match.setRefereeName(referee.getFullName());
        }
    }

    @Transactional
    public void createMatch(MatchCreation request) {
        if (Objects.equals(request.getHomeTeamId(), request.getAwayTeamId())) {
            throw new MatchCreationException("You must choose two different teams");
        }

        if (request.getStatus() == MatchStatus.UPCOMING) {
            request.setHomeScore(0);
            request.setAwayScore(0);

            if (request.getDate().isBefore(LocalDateTime.now())) {
                throw new MatchCreationException("An upcoming match cannot be scheduled in the past.");
            }
        }

        if (request.getStatus() == MatchStatus.COMPLETED && request.getDate().isAfter(LocalDateTime.now())) {
            throw new MatchCreationException("A completed match cannot be scheduled in the future.");
        }

        updateOverallStats(request);
        matchClient.createMatch(request);
    }

    private void updateOverallStats(MatchCreation request) {
        Club homeClub = clubService.getClubById(request.getHomeTeamId());
        Club awayClub = clubService.getClubById(request.getAwayTeamId());

        // increase active players matchesPlayed
        homeClub.getSquad().stream().filter(p-> p.getStatus().equals(Status.ACTIVE)).forEach(p -> p.setMatchesPlayed(p.getMatchesPlayed() + 1));
        awayClub.getSquad().stream().filter(p-> p.getStatus().equals(Status.ACTIVE)).forEach(p -> p.setMatchesPlayed(p.getMatchesPlayed() + 1));

        // increase referees matchesAttended
        Referee referee = refereeService.getRefereeById(request.getRefereeId());
        referee.setRefereeAttendance(referee.getRefereeAttendance() + 1);

        MatchStatus status = request.getStatus();

        // Increasing the number of matches the given Club participated
        homeClub.setMatchesPlayed(homeClub.getMatchesPlayed() + 1);
        awayClub.setMatchesPlayed(awayClub.getMatchesPlayed() + 1);

        //Updating stats
        if (status.equals(MatchStatus.COMPLETED)) {
            updatingClubsMatchesStats(homeClub, awayClub, request);
            return;
        }
        homeClub.setScheduledMatches(homeClub.getScheduledMatches() + 1);
        awayClub.setScheduledMatches(awayClub.getScheduledMatches() + 1);
    }

    private void updatingClubsMatchesStats(Club homeClub, Club awayClub, MatchCreation request) {
        int homeScore = request.getHomeScore();
        int awayScore = request.getAwayScore();

        homeClub.setGoalsScored(homeClub.getGoalsScored() + homeScore);
        awayClub.setGoalsScored(awayClub.getGoalsScored() + awayScore);

        if (homeScore > awayScore) {
            homeClub.setMatchesWon(homeClub.getMatchesWon() + 1);
            homeClub.setPoints(homeClub.getPoints() + 3);

            awayClub.setMatchesLost(awayClub.getMatchesLost() + 1);
        } else if (awayScore > homeScore) {
            homeClub.setMatchesLost(homeClub.getMatchesLost() + 1);
            awayClub.setPoints(awayClub.getPoints() + 3);

            awayClub.setMatchesWon(awayClub.getMatchesWon() + 1);
        } else {
            homeClub.setMatchesDrawn(homeClub.getMatchesDrawn() + 1);
            homeClub.setPoints(homeClub.getPoints() + 1);

            awayClub.setMatchesDrawn(awayClub.getMatchesDrawn() + 1);
            awayClub.setPoints(awayClub.getPoints() + 1);
        }
    }
}
