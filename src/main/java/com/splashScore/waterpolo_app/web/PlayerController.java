package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.player.model.CapNumberList;
import com.splashScore.waterpolo_app.player.service.PlayerService;
import com.splashScore.waterpolo_app.web.dto.AddPlayerRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;
    private final ClubService clubService;

    @Autowired
    public PlayerController(PlayerService playerService, ClubService clubService) {
        this.playerService = playerService;
        this.clubService = clubService;
    }

    @PostMapping("/{id}/status")
    public String changePlayerStatus(@PathVariable Long id) {
        playerService.changePlayerStatus(id);
        return "redirect:/admin-panel?activeDiv=players";
    }

    @PostMapping
    public ModelAndView savePlayer(@Valid AddPlayerRequest newPlayerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("add-player");
            mav.addObject("addPlayerRequest", newPlayerRequest);
            mav.addObject("clubs", clubService.getAllClubs());
            mav.addObject("capNumbers", CapNumberList.getCapNumbers());
            return mav;
        }
        playerService.saveNewPlayer(newPlayerRequest);

        return new ModelAndView("redirect:/admin-panel?activeDiv=players");
    }
}