package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.service.MatchService;
import com.splashScore.waterpolo_app.player.model.Country;
import com.splashScore.waterpolo_app.player.model.Player;
import com.splashScore.waterpolo_app.player.model.Status;
import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.web.dto.AddClubRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.splashScore.waterpolo_app.user.model.UserRole.ADMIN;
import static com.splashScore.waterpolo_app.user.model.UserRole.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ClubController.class)
public class ClubControllerApiTest {

    @MockitoBean
    private ClubService clubService;

    @MockitoBean
    private MatchService matchService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getRequestToClubsProfile_shouldReturnClubsProfile() throws Exception {
        AuthenticationMetaData user = principal(testUser().getId());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/clubs/{id}/profile", UUID.randomUUID())
                .with(user(user))
                .with(csrf());

        Club club = new Club(UUID.randomUUID(), "Ticha", "Varna", Country.BULGARIA);
        club.setLogoUrl("src/main/resources/images/logo.png");

        club.setSquad(List.of(new Player(UUID.randomUUID(), "Nikola", LocalDate.of(1999, 6, 9), "2", Country.BULGARIA, Status.ACTIVE, club)));

        when(clubService.getClubById(any(UUID.class))).thenReturn(club);
        when(clubService.getClubMatches(club.getId())).thenReturn(List.of(new MatchCreation()));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("club"))
                .andExpect(model().attributeExists("club", "players", "matches"));
    }

    @Test
    void getRequestToClubsDeletionPage_shouldReturnConfirmationPage() throws Exception {
        AuthenticationMetaData user = principal(testUser().getId());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/clubs/{id}/confirm-deletion", UUID.randomUUID())
                .with(user(user))
                .with(csrf());

        Club club = new Club();
        club.setName("Spartak");
        club.setSquad(List.of());

        when(clubService.getClubById(any(UUID.class))).thenReturn(club);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("confirm-club-deletion"))
                .andExpect(model().attributeExists("club"));
    }

    @Test
    void postRequestWithInvalidDataToClubs_returnTheSameView() throws Exception {
        AuthenticationMetaData user = principal(testUser().getId());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/clubs")
                .formField("name", "")
                .formField("town", "")
                .formField("country", "")
                .formField("logo_URL", "")
                .with(user(user))
                .with(csrf());


        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("add-club"))
                .andExpect(model().attributeExists("addClubRequest", "clubs"));

        verify(clubService, never()).saveNewClub(any(AddClubRequest.class));
    }

    @Test
    void postRequestToClubs_happyPath() throws Exception {
        AuthenticationMetaData user = principal(testUser().getId());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/clubs")
                .formField("name", "Spartak")
                .formField("town", "Varna")
                .formField("country", "BULGARIA")
                .formField("logo_URL", "vfvsvvsv")
                .with(user(user))
                .with(csrf());


        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-panel?activeDiv=clubs"));

        verify(clubService, times(1)).saveNewClub(any(AddClubRequest.class));
    }

    private AuthenticationMetaData principal(UUID userId) {
        return new AuthenticationMetaData(userId,
                "nik123",
                "123123",
                "admin@gmail.com",
                ADMIN,
                LocalDateTime.now());
    }

    private User testUser() {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setUsername("admin");
        testUser.setEmail("admin@gmail.com");
        testUser.setPassword("123123");
        testUser.setRole(USER);
        testUser.setCreatedOn(LocalDateTime.now());

        return testUser;
    }
}
