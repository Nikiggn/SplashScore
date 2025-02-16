package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.service.UserService;
import com.splashScore.waterpolo_app.web.dto.LoginRequest;
import com.splashScore.waterpolo_app.web.dto.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private final UserService userService;

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public ModelAndView getIndexPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("index");

        Long userId = (Long) session.getAttribute("user_id");
        if (userId != null) {
            User user = userService.getUserById(userId);
            modelAndView.addObject("user", user);
        } else {
            modelAndView.addObject("user", null);
        }

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("registerRequest", new RegisterRequest());

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@Valid RegisterRequest registerRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        User user = userService.register(registerRequest);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);

        modelAndView.setViewName("redirect:/login");

        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("loginRequest", new LoginRequest());

        return modelAndView;
    }

    @PostMapping("/login")
    public String login(@Valid LoginRequest loginRequest, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        User user  = userService.login(loginRequest);
        session.setAttribute("user_id", user.getId());

        return "redirect:/";
    }


    @GetMapping("/admin-panel")
    public ModelAndView getAdminPanelPage(HttpSession session) {
        User user = userService.getUserById((Long) session.getAttribute("user_id"));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin-panel");
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
