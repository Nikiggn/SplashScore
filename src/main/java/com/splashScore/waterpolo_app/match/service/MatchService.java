package com.splashScore.waterpolo_app.match.service;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.match.MatchClient;
import com.splashScore.waterpolo_app.match.MatchStatus;
import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.dto.MatchView;
import com.splashScore.waterpolo_app.match.dto.MatchViewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class MatchService {
    private final MatchClient matchClient;
    private final ClubService clubService;

    @Autowired
    public MatchService(MatchClient matchClient, ClubService clubService) {
        this.matchClient = matchClient;
        this.clubService = clubService;
    }

    public List<MatchView> getAllMatchesWithClubDetails(){
        List<MatchViewResponse> matches = matchClient.getAllMatches().getBody();

        assert matches != null;

        return  matches.stream().map(match -> {
            Club homeClub = clubService.getClubById(match.getHomeTeam());
            Club awayClub = clubService.getClubById(match.getAwayTeam());
            return new MatchView(homeClub, awayClub, match.getDate(), match.getStatus());
        }).toList();
    }

    public void createMatch(MatchCreation request){
        if (Objects.equals(request.getHomeTeamId(), request.getAwayTeamId())){
            throw new DomainException("You must choose two different teams");
        }

        if (request.getStatus() == MatchStatus.UPCOMING){
            request.setHomeScore(0);
            request.setAwayScore(0);

            if (request.getDate().isBefore(LocalDateTime.now())){
                throw new DomainException("An upcoming match cannot be scheduled in the past.");
            }
        }

        if (request.getStatus() == MatchStatus.COMPLETED && request.getDate().isAfter(LocalDateTime.now())) {
            throw new DomainException("A completed match cannot be scheduled in the future.");
        }

        matchClient.createMatch(request);
    }
}
