package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.player.model.Player;
import com.splashScore.waterpolo_app.player.service.PlayerService;
import com.splashScore.waterpolo_app.referee.model.Referee;
import com.splashScore.waterpolo_app.referee.service.RefereeService;
import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.service.UserService;
import com.splashScore.waterpolo_app.web.dto.LoginRequest;
import com.splashScore.waterpolo_app.web.dto.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    private final UserService userService;
    private final PlayerService playerService;
    private final ClubService clubService;
    private final RefereeService refereeService;

    @Autowired
    public IndexController(UserService userService, PlayerService playerService, ClubService clubService, RefereeService refereeService) {
        this.userService = userService;
        this.playerService = playerService;
        this.clubService = clubService;
        this.refereeService = refereeService;
    }

    @GetMapping("/")
    public ModelAndView getIndexPage(@AuthenticationPrincipal AuthenticationMetaData authenticationMetaData) {
        ModelAndView modelAndView = new ModelAndView("index");

        // Check if a user is authenticated
        if (authenticationMetaData != null) {
            User user = userService.getUserById(authenticationMetaData.getId());
            modelAndView.addObject("user", user);
        } else {
            modelAndView.addObject("user", null); // or handle anonymous users differently
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
    public ModelAndView getLoginPage(@RequestParam(value = "error", required = false) String errorParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("loginRequest", new LoginRequest());

        if (errorParam != null) {
            modelAndView.addObject("errorMessage", "Incorrect username or password.");
        }
        return modelAndView;
    }

    @GetMapping("/admin-panel")  // казваме на Spring дай ми този обект
    @PreAuthorize("hasRole('ADMIN')") //проверяваме за едн аконкретна роля
    public ModelAndView getAdminPanelPage(@AuthenticationPrincipal AuthenticationMetaData authenticationMetaData) {
        //този който иска да достъпи home page-a кой е
        User user = userService.getUserById(authenticationMetaData.getId());

        List<Player> players = playerService.getAllPlayers();
        List<User> users = userService.getAllUsers(user);
        List<Club> clubs = clubService.getAllClubs();
        List<Referee> referees = refereeService.getAllReferees();

        ModelAndView mav = new ModelAndView();

        mav.setViewName("admin-panel");
        mav.addObject("users", users);
        mav.addObject("players", players);
        mav.addObject("user", user);
        mav.addObject("clubs", clubs);
        mav.addObject("referees", referees);

        return mav;
    }
}

// Role -> groups of permissions
// Permission -> authority