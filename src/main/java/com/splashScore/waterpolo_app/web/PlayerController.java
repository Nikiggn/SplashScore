package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.player.model.Player;
import com.splashScore.waterpolo_app.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/{id}/change-status")
    public String changePlayerStatus(@PathVariable Long id) {
        playerService.changePlayerStatus(id);
        return "redirect:/admin-panel?activeDiv=players";  // or whichever divId you want active
    }
}