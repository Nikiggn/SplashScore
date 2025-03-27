package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.referee.service.RefereeService;
import com.splashScore.waterpolo_app.web.dto.AddRefereeRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/referees")
public class RefereeController {
    private final RefereeService refereeService;

    @Autowired
    public RefereeController(RefereeService refereeService) {
        this.refereeService = refereeService;
    }

    @PostMapping
    public ModelAndView addReferee(@Valid AddRefereeRequest addRefereeRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("addReferee");
            modelAndView.addObject("addRefereeRequest", addRefereeRequest);
            return modelAndView;
        }
        refereeService.saveNewReferee(addRefereeRequest);

        return new ModelAndView("redirect:/admin-panel?activeDiv=referees");
    }

    @PostMapping("/{id}/status")
    public String changeRefereeStatus(@PathVariable UUID id){
        refereeService.changeRefereeStatus(id);
        return "redirect:/admin-panel?activeDiv=referees";
    }
}
