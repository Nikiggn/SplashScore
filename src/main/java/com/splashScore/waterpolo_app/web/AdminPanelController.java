package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.splashScore.waterpolo_app.user.model.User;


import java.util.List;

@Controller
public class AdminPanelController {
    private final UserService userService;

    @Autowired
    public AdminPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin-panel/users")
    public String getAllRegisteredUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-panel-users"; // Return only the fragment
    }

    // Add similar methods for other sections (dashboard, players, matches, etc.)
}

