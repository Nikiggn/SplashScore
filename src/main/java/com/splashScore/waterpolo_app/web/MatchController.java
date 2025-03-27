package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/matches")
public class MatchController {
    private final MatchService matchService;
    private final ClubService clubService;

    @Autowired
    public MatchController(MatchService matchService, ClubService clubService) {
        this.matchService = matchService;
        this.clubService = clubService;
    }

    @PostMapping
    public String createMatch(@ModelAttribute MatchCreation request) {
        matchService.createMatch(request);
        return "redirect:/admin-panel?activeDiv=matches";
    }


}
