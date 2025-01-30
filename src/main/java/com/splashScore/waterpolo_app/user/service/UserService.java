package com.splashScore.waterpolo_app.user.service;

import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.user.model.Role;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.repository.UserRepository;
import com.splashScore.waterpolo_app.web.dto.LoginRequest;
import com.splashScore.waterpolo_app.web.dto.RegisterRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // -> при създава на инстанция UserService
    // искам да ми дадеш имплементация на userRepository (дай ми депендънси)
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Transactional// гарантитра всяка от долните операции може да се изпълни
    public User register(RegisterRequest registerRequest) {
        Optional<User> userOptional = userRepository.findByUsername(registerRequest.getUsername());

        if (userOptional.isPresent()) {
            throw new DomainException("Username is already in use");
        }

        User user = userRepository.save(initializUser(registerRequest));

        logger.info("Successfully  created user account for username [%s] and id [%d]:".formatted(user.getUsername(), user.getId()));
        return user;
    }

    public User login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
            throw new DomainException("Invalid username or password");
        }

        return userOptional.get();
    }

    private User initializUser(RegisterRequest registerRequest) {
        User user = modelMapper.map(registerRequest, User.class);

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedOn(LocalDateTime.now());
        user.setRole(Role.USER);

        return user;
    }

    public List<User> getAllUsers() {
        return  userRepository.findAll();
    }
}
