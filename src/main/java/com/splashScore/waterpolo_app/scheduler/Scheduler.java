package com.splashScore.waterpolo_app.scheduler;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.match.client.MatchClient;
import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.dto.MatchForProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {
     private final ClubService clubService;

    private final MatchClient matchClient;

    @Autowired
    public Scheduler(ClubService clubService, MatchClient matchClient) {
        this.clubService = clubService;
        this.matchClient = matchClient;
    }

//    @Scheduled(fixedDelay = 200000)
//    public void fnfnv(){
//       // List<MatchCreation> matches = matchClient.getAllMatches();
//
//        List<Club> clubs = clubService.getAllClubs();
//
//        clubs.forEach(club -> {
//            List<MatchCreation> matches = clubService.getClubMatches(club.getId()).stream().filter(m-> !m.isProcessed()).toList();
//
//           matches.forEach(match -> {
//                int homeScore = match.getHomeScore();
//                int awayScore = match.getAwayScore();
//
//                if (homeScore > awayScore && awayScore == 0) {
//                    club.setShutoutWins(club.getShutoutWins() + 1);
//                }else if (awayScore > homeScore && homeScore == 0) {
//                    club.setShutoutWins(club.getShutoutWins() + 1);
//                }
//
//                match.setProcessed(true);
//           });
//        });






    @Scheduled(fixedDelay = 60000)  // Run every 200 seconds
    public void processMatches() {
        System.out.println("Processing Matches");
        List<MatchForProcessing> matchDTOs = matchClient.getUnprocessedMatches();  // Fetch unprocessed matches as DTO

        for (MatchForProcessing matchDTO : matchDTOs) {
            int homeScore = matchDTO.getHomeScore();
            int awayScore = matchDTO.getAwayScore();

            // Check if home team won with a shutout
            if (homeScore > awayScore && awayScore == 0) {
                Club homeClub = clubService.getClubById(matchDTO.getHomeClubId());
                homeClub.setShutoutWins(homeClub.getShutoutWins() + 1);
                clubService.save(homeClub);  // Save updated club data
            }

            // Check if away team won with a shutout
            if (awayScore > homeScore && homeScore == 0) {
                Club awayClub = clubService.getClubById(matchDTO.getAwayClubId());
                awayClub.setShutoutWins(awayClub.getShutoutWins() + 1);
                clubService.save(awayClub);  // Save updated club data
            }

            // Mark match as processed
            matchDTO.setProcessed(true);
            matchClient.updateMatchStatus(matchDTO.getId(), matchDTO);  // Update match status in microservice
        }
    }
}
