package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/clubs")
public class ClubController {
    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/{id}/confirm-deletion")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getConfirmationPage(@PathVariable Long id) {
        Club club = clubService.getClubById(id);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("confirm-club-deletion");
        mav.addObject("club", club);

        return mav;
    }

    @PostMapping("/{id}/deletion")
    public String deleteClub(@PathVariable Long id) {
        clubService.deleteClubById(id);
        return "redirect:/admin-panel?activeDiv=clubs";  // or whichever divId you want active
    }

}
