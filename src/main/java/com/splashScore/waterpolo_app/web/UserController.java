package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.model.UserRole;
import com.splashScore.waterpolo_app.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ModelAndView getUserProfile(@AuthenticationPrincipal AuthenticationMetaData authenticationMetaData) {
        User user = userService.getUserById(authenticationMetaData.getId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @PostMapping("/{id}/role")
    public String changeUserRole(@PathVariable("id") UUID targetUserId, @AuthenticationPrincipal AuthenticationMetaData admin) {
        userService.changeUserRole(targetUserId, admin.getId());
        return "redirect:/admin-panel?activeDiv=users";  // or whichever divId you want active
    }
}
