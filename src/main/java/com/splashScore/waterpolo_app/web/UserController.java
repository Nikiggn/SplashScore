package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

        System.out.println("AuthenticationMetaData: " + authenticationMetaData); // Debug log

        if (authenticationMetaData == null) {
            throw new RuntimeException("AuthenticationMetaData is NULL!");
        }
        User user = userService.getUserById(authenticationMetaData.getId());

        System.out.println("Authenticated user ID: " + authenticationMetaData.getId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
