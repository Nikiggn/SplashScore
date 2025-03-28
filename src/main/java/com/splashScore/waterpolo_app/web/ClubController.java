package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.web.dto.AddClubRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/clubs")
public class ClubController {
    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping
    public ModelAndView addClub(@Valid AddClubRequest newClubRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("add-club");
            mav.addObject("addClubRequest", new AddClubRequest());
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

    @PostMapping("/{id}/deletion")
    public String deleteClub(@PathVariable UUID id) {
        clubService.deleteClubById(id);
        return "redirect:/admin-panel?activeDiv=clubs";  // or whichever divId you want active
    }
}
