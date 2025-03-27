package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.player.model.CapNumberList;
import com.splashScore.waterpolo_app.player.service.PlayerService;
import com.splashScore.waterpolo_app.referee.model.Status;
import com.splashScore.waterpolo_app.referee.service.RefereeService;
import com.splashScore.waterpolo_app.web.dto.AddClubRequest;
import com.splashScore.waterpolo_app.web.dto.AddPlayerRequest;
import com.splashScore.waterpolo_app.web.dto.AddRefereeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin-panel")
public class AdminPanelController {
    private final PlayerService playerService;
    private final ClubService clubService;
    private final RefereeService refereeService;

    @Autowired
    public AdminPanelController(PlayerService playerService, ClubService clubService, RefereeService refereeService) {
        this.playerService = playerService;
        this.clubService = clubService;
        this.refereeService = refereeService;
    }

    @GetMapping("/players/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAddPlayerPage() {
        playerService.checkAvailableClubs();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("add-player");
        mav.addObject("addPlayerRequest", new AddPlayerRequest());
        mav.addObject("clubs", clubService.getAllClubs());
        mav.addObject("capNumbers", CapNumberList.getCapNumbers());
        return mav;
    }

    @GetMapping("/clubs/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAddClubPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("add-club");
        mav.addObject("addClubRequest", new AddClubRequest());
        mav.addObject("clubs", clubService.getAllClubs());
        return mav;
    }

    @GetMapping("/matches/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAddMatchPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("add-match");
        mav.addObject("addMatchRequest",new MatchCreation());
        mav.addObject("clubs", clubService.getAllClubs());
        mav.addObject("referees", refereeService.getAllReferees());

        return mav;
    }

    @GetMapping("/referees/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAddRefereePage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("add-referee");
        mav.addObject("addRefereeRequest", new AddRefereeRequest());
        return mav;
    }
}

