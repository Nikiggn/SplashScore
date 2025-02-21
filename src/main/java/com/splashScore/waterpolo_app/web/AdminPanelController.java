//package com.splashScore.waterpolo_app.web;
//
// import com.splashScore.waterpolo_app.player.model.Player;
//import com.splashScore.waterpolo_app.player.service.PlayerService;
//import com.splashScore.waterpolo_app.user.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//import com.splashScore.waterpolo_app.user.model.User;
//
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin-panel")
//public class AdminPanelController {
//    private final UserService userService;
//    private final PlayerService playerService;
//
//    @Autowired
//    public AdminPanelController(UserService userService, PlayerService playerService) {
//        this.userService = userService;
//        this.playerService = playerService;
//    }
//
////    @GetMapping("/admin-panel")
////    public ModelAndView getAdminPanel() {
////        List<Player> players = playerService.getAllPlayers();
////        List<User> users = userService.getAllUsers();
////
////        ModelAndView modelAndView = new ModelAndView();
////        modelAndView.addObject("users", users);
////        modelAndView.addObject("players", players);
////
////        return modelAndView;
////    }
//    // Add similar methods for other sections (dashboard, players, matches, etc.)
//}
//
