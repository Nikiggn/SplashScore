package com.splashScore.waterpolo_app.user.service;

import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.user.model.UserRole;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.repository.UserRepository;
import com.splashScore.waterpolo_app.web.dto.LoginRequest;
import com.splashScore.waterpolo_app.web.dto.RegisterRequest;
import org.modelmapper.ModelMapper;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
 import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService{//  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

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

        return userRepository.save(initializeUser(registerRequest));
    }

//    public User login(LoginRequest loginRequest) {
//        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
//
//        if (userOptional.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
//            throw new DomainException("Invalid username or password");
//        }
//
//        return userOptional.get();
//    }

    private User initializeUser(RegisterRequest registerRequest) {
        User user = modelMapper.map(registerRequest, User.class);

        System.out.println("Encoded Password: " + passwordEncoder.encode(registerRequest.getPassword()));
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedOn(LocalDateTime.now());
        user.setRole(UserRole.USER);

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

//за да вземем детайлите на потребителя с този username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new DomainException("User not found"));

        return new AuthenticationMetaData(user.getId(), username, user.getPassword(),user.getEmail(), user.getRole(), user.getCreatedOn());
    }
}
