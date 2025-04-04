package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.dto.MatchView;
import com.splashScore.waterpolo_app.match.service.MatchService;
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
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    private final UserService userService;
    private final PlayerService playerService;
    private final ClubService clubService;
    private final RefereeService refereeService;
    private final MatchService matchService;

    private static final int PAGE_SIZE = 8; // Max number of players per page
    private static final int PAGE_SIZE_MATCHES = 5; // Max number of players per page

// Role -> groups of permissions
// Permission -> authority

    @Autowired
    public IndexController(UserService userService, PlayerService playerService, ClubService clubService, RefereeService refereeService, MatchService matchService) {
        this.userService = userService;
        this.playerService = playerService;
        this.clubService = clubService;
        this.refereeService = refereeService;
        this.matchService = matchService;
    }

    @GetMapping("/")
    public ModelAndView getIndexPage(@RequestParam(defaultValue = "1") int page, @AuthenticationPrincipal AuthenticationMetaData authenticationMetaData) {
        ModelAndView modelAndView = new ModelAndView("index");

        User user = userService.getUserById(authenticationMetaData.getId());
        List<Club> clubs = clubService.getAllClubsSortedByPoints();

        List<MatchCreation> matches = matchService.getMatchesByPage(page, PAGE_SIZE_MATCHES);
        int totalMatches = matchService.getAllMatchesWithClubDetails().size();
        int totalPages = (int) Math.ceil((double) totalMatches / PAGE_SIZE_MATCHES);

        matchService.addClubAndRefereeDetailsToEachMatch(matches);

        modelAndView.addObject("user", user);
        modelAndView.addObject("clubs", clubs);
        modelAndView.addObject("matches", matches);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", totalPages);

        return modelAndView;
    }

    @GetMapping("/players")
    public ModelAndView getPlayersPage(@RequestParam(defaultValue = "1") int page, @AuthenticationPrincipal AuthenticationMetaData authenticationMetaData) {
        User user = userService.getUserById(authenticationMetaData.getId());

        List<Player> players = playerService.getPlayersByPage(page, PAGE_SIZE);
        int totalPlayers = playerService.getTotalPlayerCount();
        int totalPages = (int) Math.ceil((double) totalPlayers / PAGE_SIZE);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("players");
        modelAndView.addObject("user", user);
        modelAndView.addObject("players", players);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", totalPages);

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
        userService.register(registerRequest);

        ModelAndView modelAndView = new ModelAndView();
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
        List<Club> clubs = clubService.getAllClubs() ;
        List<Referee> referees = refereeService.getAllReferees();
        List<MatchView> matches = matchService.getAllMatchesWithClubDetails();

        ModelAndView mav = new ModelAndView();

        mav.setViewName("admin-panel");
        mav.addObject("users", users);
        mav.addObject("players", players);
        mav.addObject("user", user);
        mav.addObject("clubs", clubs);
        mav.addObject("referees", referees);
        mav.addObject("matches", matches);

        return mav;
    }

    @GetMapping("/settings")
    public String getSettingsPage() {
        return "settings";
    }
}