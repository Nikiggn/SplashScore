package com.splashScore.waterpolo_app.user.service;

import com.splashScore.waterpolo_app.web.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserInit implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public UserInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userService.getAllUsers().isEmpty()){
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("admin");
        registerRequest.setPassword("123456");
        registerRequest.setEmail("admin@gmail.com");

        userService.register(registerRequest);

    }
}
