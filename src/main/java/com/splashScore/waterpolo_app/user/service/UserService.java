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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private User initializeUser(RegisterRequest registerRequest) {
        User user = modelMapper.map(registerRequest, User.class);

        System.out.println("Encoded Password: " + passwordEncoder.encode(registerRequest.getPassword()));
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedOn(LocalDateTime.now());
        user.setRole(UserRole.USER);

        return user;
    }

    public List<User> getAllUsers(User user) {
        return userRepository.findAll().stream().filter(u -> !Objects.equals(u.getId(), user.getId())).collect(Collectors.toList());
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public void changeUserRole(Long targetUserId, Long adminId) {
        if (Objects.equals(targetUserId, adminId)) {
            throw new DomainException("Admin cannot change their own role. Please contact system support if needed.");
        }

        User user = userRepository.findById(targetUserId).orElseThrow();

        if (user.getRole() == UserRole.USER){
            user.setRole(UserRole.ADMIN);
        }else {
            user.setRole(UserRole.USER);
        }
    }

    //за да вземем детайлите на потребителя с този username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new DomainException("User not found"));

        return new AuthenticationMetaData(user.getId(), username, user.getPassword(),user.getEmail(), user.getRole(), user.getCreatedOn());
    }
}
