package com.splashScore.waterpolo_app.match.client;

import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.dto.MatchForProcessing;
import com.splashScore.waterpolo_app.match.dto.MatchViewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "matches-svc", url = "http://localhost:8081/api/v1/matches")
public interface MatchClient {

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createMatch(@RequestBody MatchCreation match);

    @GetMapping
    ResponseEntity<List<MatchCreation>> getAllMatches();

    @GetMapping("/club")
    List<MatchCreation> getMatchesByClubId(@RequestParam("clubId") UUID clubId);


    @GetMapping("/page")
    List<MatchCreation> getMatchesByPage(@RequestParam("page") int page, @RequestParam("size") int size);


    @GetMapping("/unprocessed")
    List<MatchForProcessing> getUnprocessedMatches();  // Fetch unprocessed matches as DTO

    @PutMapping("/{id}")
    void updateMatchStatus(@PathVariable("id") UUID matchId, @RequestBody MatchForProcessing matchDTO);  // Update match status via DTO

}
