package com.splashScore.waterpolo_app.user.service;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.repository.ClubRepository;
import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.exception.EmailAlreadyExistException;
import com.splashScore.waterpolo_app.exception.UsernameAlreadyExistException;
import com.splashScore.waterpolo_app.player.model.Country;
import com.splashScore.waterpolo_app.player.model.Player;
import com.splashScore.waterpolo_app.player.model.Status;
import com.splashScore.waterpolo_app.player.repository.PlayerRepository;
import com.splashScore.waterpolo_app.referee.model.Referee;
import com.splashScore.waterpolo_app.referee.repository.RefereeRepository;
import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.user.model.UserRole;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.repository.UserRepository;
import com.splashScore.waterpolo_app.web.dto.RegisterRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
    //!!!!!!!Цел -> Помощ
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final RefereeRepository refereeRepository;
    //!!!!!!!

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // -> при създава на инстанция UserService
    // искам да ми дадеш имплементация на userRepository (дай ми депендънси)
    @Autowired
    public UserService(PlayerRepository playerRepository, ClubRepository clubRepository, RefereeRepository refereeRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
        this.refereeRepository = refereeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Transactional// гарантитра всяка от долните операции може да се изпълни
    public User register(RegisterRequest registerRequest) {
        userRepository.findByUsername(registerRequest.getUsername())
                .ifPresent(user -> {
                    throw new UsernameAlreadyExistException(registerRequest.getUsername());
                });

        userRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistException(registerRequest.getEmail());
                });

        return userRepository.save(initializeUser(registerRequest));
    }

    private User initializeUser(RegisterRequest registerRequest) {
        User user = modelMapper.map(registerRequest, User.class);

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedOn(LocalDateTime.now());
        user.setRole(UserRole.USER);

        // !!!!!
        if (userRepository.findAll().isEmpty()) {
            user.setRole(UserRole.ADMIN);
            fillData();
        }
        // !!!!!

        return user;
    }

    public List<User> getAllUsers(User user) {
        return userRepository.findAll()
                .stream()
                .filter(u -> !Objects.equals(u.getId(), user.getId()))
                .sorted(Comparator.comparing(User::getRole))
                .collect(Collectors.toList());
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DomainException("User not found with id: " + userId));
    }

    @Transactional
    public void changeUserRole(UUID targetUserId, UUID adminId) {
        if (Objects.equals(targetUserId, adminId)) {
            throw new DomainException("Admin cannot change their own role. Please contact system support if needed.");
        }

        User user = userRepository.findById(targetUserId).orElseThrow(() -> new DomainException("User not found with id: " + targetUserId));

        if (user.getRole() == UserRole.USER) {
            user.setRole(UserRole.ADMIN);
        } else {
            user.setRole(UserRole.USER);
        }
    }

    //за да вземем детайлите на потребителя с този username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new DomainException("User not found with username: " + username));

        return new AuthenticationMetaData(user.getId(), username, user.getPassword(), user.getEmail(), user.getRole(), user.getCreatedOn());
    }

    //!!!!!!!!!!!!
    // За Ваше улеснение
    // !!!!!!!!!!!
    protected void fillData() {
        Club club = new Club(null, "Cherno-More", "Varna", Country.BULGARIA);
        Club club2 = new Club(null, "Spartak", "Varna", Country.BULGARIA);
        Club club3 = new Club(null, "CSKA-Sofia", "Sofia", Country.BULGARIA);
        Club club4 = new Club(null, "Chernomorec", "Burgas", Country.BULGARIA);
        Club club5 = new Club(null, "Akva", "Varna", Country.BULGARIA);
        List<Club> clubs = Arrays.asList(club, club2, club3, club4, club5);

        List<String> logos = new ArrayList<>(List.of("logo-3.png", "logo-4.png",
                "logo-5.png", "logo-6.png", "logo-7.jpg"
        ));
        Collections.shuffle(logos);

        for (int i = 0; i < clubs.size(); i++) {
            String assignedLogo = (i < logos.size()) ? logos.get(i) : "logo-1.jpg"; // Use default if out of logos
            clubs.get(i).setLogoUrl("/images/" + assignedLogo);

        }

        Player player = new Player(null, "Nikola Antoniev", LocalDate.of(2007, 9, 14), "2", Country.BULGARIA, Status.ACTIVE, club);
        Player player2 = new Player(null, "Ivaylo Antoniev", LocalDate.of(2002, 2, 24), "5", Country.BULGARIA, Status.ACTIVE, club);
        Player player3 = new Player(null, "Martin Karanfilov", LocalDate.of(2007, 9, 14), "4", Country.BULGARIA, Status.ACTIVE, club2);
        Player player4 = new Player(null, "Veselin Ganchev", LocalDate.of(2007, 5, 6), "10", Country.BULGARIA, Status.ACTIVE, club2);
        Player player5 = new Player(null, "Petur Stoqnov", LocalDate.of(1999, 6, 9), "7", Country.BULGARIA, Status.ACTIVE, club3);
        Player player6 = new Player(null, "Ivan Ivanov", LocalDate.of(2007, 9, 14), "3", Country.BULGARIA, Status.ACTIVE, club3);
        Player player7 = new Player(null, "Stoqn Pet", LocalDate.of(1990, 5, 4), "4", Country.BULGARIA, Status.ACTIVE, club4);
        Player player8 = new Player(null, "Cristiano Ron", LocalDate.of(1995, 2, 4), "7", Country.BULGARIA, Status.ACTIVE, club4);

        Referee referee = new Referee(null, "Ivan Kik", Country.ROMANIA, com.splashScore.waterpolo_app.referee.model.Status.ACTIVE);
        Referee referee2 = new Referee(null, "Boris Bob", Country.BULGARIA, com.splashScore.waterpolo_app.referee.model.Status.ACTIVE);

        clubRepository.save(club);
        clubRepository.save(club2);
        clubRepository.save(club3);
        clubRepository.save(club4);
        playerRepository.save(player);
        playerRepository.save(player2);
        playerRepository.save(player3);
        playerRepository.save(player4);
        playerRepository.save(player5);
        playerRepository.save(player6);
        playerRepository.save(player7);
        playerRepository.save(player8);
        refereeRepository.save(referee);
        refereeRepository.save(referee2);

    }
}
