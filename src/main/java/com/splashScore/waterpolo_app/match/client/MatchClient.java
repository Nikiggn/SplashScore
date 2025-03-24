package com.splashScore.waterpolo_app.match.client;

import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.dto.MatchViewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "matches-svc", url = "http://localhost:8081/api/v1/matches")
public interface MatchClient {

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createMatch(@RequestBody MatchCreation match);

    @GetMapping
    ResponseEntity<List<MatchViewResponse>> getAllMatches();
}
