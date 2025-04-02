package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.service.MatchService;
import com.splashScore.waterpolo_app.player.model.Player;
import com.splashScore.waterpolo_app.referee.model.Referee;
import com.splashScore.waterpolo_app.referee.service.RefereeService;
import com.splashScore.waterpolo_app.web.dto.AddClubRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/clubs")
public class ClubController {
    private final ClubService clubService;
    private final MatchService matchService;

    @Autowired
    public ClubController(ClubService clubService, MatchService matchService) {
        this.clubService = clubService;
        this.matchService = matchService;
    }

    @PostMapping
    public ModelAndView addClub(@Valid AddClubRequest newClubRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("add-club");
            mav.addObject("addClubRequest", newClubRequest);
            mav.addObject("clubs", clubService.getAllClubs());
            return mav;
        }
        clubService.saveNewClub(newClubRequest);

        return new ModelAndView("redirect:/admin-panel?activeDiv=clubs");
    }

    @GetMapping("/{id}/confirm-deletion")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getConfirmationPage(@PathVariable UUID id) {
        Club club = clubService.getClubById(id);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("confirm-club-deletion");
        mav.addObject("club", club);

        return mav;
    }

    @DeleteMapping("/{id}/deletion")
    public String deleteClub(@PathVariable UUID id) {
        clubService.deleteClubById(id);
        return "redirect:/admin-panel?activeDiv=clubs";  // or whichever divId you want active
    }

    @GetMapping("/clubs/{id}/matches")
    public List<MatchCreation> getClubMatches(@PathVariable UUID id) {
        return clubService.getClubMatches(id);
    }

    @GetMapping("/{id}/profile")
    public ModelAndView getClubProfile(@PathVariable UUID id) {
        ModelAndView mav = new ModelAndView();

        Club club = clubService.getClubById(id);
        List<Player> players = club.getSquad();
        List<MatchCreation> matches = clubService.getClubMatches(id);

        matchService.addClubAndRefereeDetailsToEachMatch(matches);

        mav.setViewName("club");
        mav.addObject("club", club);
        mav.addObject("players", players);
        mav.addObject("matches", matches);

        return mav;
    }
}
