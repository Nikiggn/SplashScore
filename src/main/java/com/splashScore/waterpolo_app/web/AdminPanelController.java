package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.player.model.CapNumberList;
import com.splashScore.waterpolo_app.player.service.PlayerService;
import com.splashScore.waterpolo_app.web.dto.AddClubRequest;
import com.splashScore.waterpolo_app.web.dto.AddPlayerRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin-panel")
public class AdminPanelController {
    private final PlayerService playerService;
    private final ClubService clubService;

    @Autowired
    public AdminPanelController(PlayerService playerService, ClubService clubService) {
        this.playerService = playerService;
        this.clubService = clubService;
    }

    @GetMapping("/add-player")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAddPlayerPage() {
        return addPlayerMV(new AddPlayerRequest());
    }

    @PostMapping("/add-player")
    public ModelAndView saveAddPlayer(@Valid AddPlayerRequest newPlayerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return addPlayerMV(newPlayerRequest);
        }
        playerService.saveNewPlayer(newPlayerRequest);

        return new ModelAndView("redirect:/admin-panel?activeDiv=players");
    }

    @GetMapping("/add-club")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAddClubPage() {
        return addClubMV(new AddClubRequest());
    }

    @PostMapping("/add-club")
    public ModelAndView saveAddClub(@Valid AddClubRequest newClubRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return addClubMV(newClubRequest);
        }

        clubService.saveNewClub(newClubRequest);

        return new ModelAndView("redirect:/admin-panel?activeDiv=clubs");
    }

    private ModelAndView addPlayerMV(AddPlayerRequest addPlayerRequest) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("add-player");
        mav.addObject("addPlayerRequest", addPlayerRequest);
        mav.addObject("clubs", clubService.getAllClubs());
        mav.addObject("capNumbers", CapNumberList.getCapNumbers());
        return mav;
    }

    private ModelAndView addClubMV(AddClubRequest addClubRequest) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("add-club");
        mav.addObject("addClubRequest", addClubRequest);
        mav.addObject("clubs", clubService.getAllClubs());
        return mav;
    }
}

