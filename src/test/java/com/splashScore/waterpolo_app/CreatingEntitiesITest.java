package com.splashScore.waterpolo_app;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.repository.ClubRepository;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.match.MatchStatus;
import com.splashScore.waterpolo_app.match.client.MatchClient;
import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.service.MatchService;
import com.splashScore.waterpolo_app.player.model.Country;
import com.splashScore.waterpolo_app.player.service.PlayerService;
import com.splashScore.waterpolo_app.referee.model.Referee;
import com.splashScore.waterpolo_app.referee.service.RefereeService;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.service.UserService;
import com.splashScore.waterpolo_app.web.dto.AddClubRequest;
import com.splashScore.waterpolo_app.web.dto.AddRefereeRequest;
import com.splashScore.waterpolo_app.web.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
  public class CreatingEntitiesITest {
    @Autowired
    private ClubService clubService;

    @Autowired
    private RefereeService refereeService;

    @Autowired
    private UserService userService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MatchClient matchClient; // Mock external service

    @Test
    void whenCreatingClub_thenClubIsSaved() {
        // Given
        AddClubRequest addClubRequest = new AddClubRequest("Ticha", "Varna", Country.BULGARIA, "gsbgbsgbg");

        // When
        Club club = clubService.saveNewClub(addClubRequest);

        // Then
        assertNotNull(club);
        assertEquals("Ticha", club.getName());
        assertEquals("Varna", club.getTown());
    }

    @Test
    void whenCreatingReferee_thenRefereeIsSaved() {
        // Given
        AddRefereeRequest addRefereeRequest = new AddRefereeRequest("Anto123", Country.BULGARIA);

        // When
        Referee referee = refereeService.saveNewReferee(addRefereeRequest);

        // Then
        assertNotNull(referee);
        assertEquals("Anto123", referee.getFullName());
        assertEquals(Country.BULGARIA, referee.getCountry());
    }
}

