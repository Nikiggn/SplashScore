package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.player.service.PlayerService;
import com.splashScore.waterpolo_app.referee.service.RefereeService;
import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.splashScore.waterpolo_app.user.model.UserRole.ADMIN;
import static com.splashScore.waterpolo_app.user.model.UserRole.USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminPanelController.class)
public class AdminPanelApiTest {

    @MockitoBean
    private PlayerService playerService;
    @MockitoBean
    private ClubService clubService;
    @MockitoBean
    private RefereeService refereeService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getUnauthenticatedRequestToAddReferee_shouldTrowException() throws Exception {
        AuthenticationMetaData principal = principal(UUID.randomUUID());
        principal.setRole(USER);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/admin-panel/referees/new")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(view().name("not-found"));
    }

    @Test
    void getAuthenticatedRequestToAddReferee_shouldRedirect() throws Exception {
        AuthenticationMetaData principal = principal(UUID.randomUUID());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/admin-panel/referees/new")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("add-referee"))
                .andExpect(model().attributeExists("addRefereeRequest"));
    }

    @Test
    void getUnauthenticatedRequestToAddMatch_shouldTrowException() throws Exception {
        AuthenticationMetaData principal = principal(UUID.randomUUID());
        principal.setRole(USER);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/admin-panel/matches/new")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(view().name("not-found"));
    }

    @Test
    void getAuthenticatedRequestToAddMatch_shouldRedirect() throws Exception {
        AuthenticationMetaData principal = principal(UUID.randomUUID());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/admin-panel/matches/new")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("add-match"))
                .andExpect(model().attributeExists("addMatchRequest", "clubs", "referees"));
    }


    @Test
    void getUnauthenticatedRequestToAddClub_shouldTrowException() throws Exception {
        AuthenticationMetaData principal = principal(UUID.randomUUID());
        principal.setRole(USER);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/admin-panel/clubs/new")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(view().name("not-found"));
    }

    @Test
    void getAuthenticatedRequestToAddClub_shouldRedirect() throws Exception {
        AuthenticationMetaData principal = principal(UUID.randomUUID());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/admin-panel/clubs/new")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("add-club"))
                .andExpect(model().attributeExists("addClubRequest", "clubs"));
    }

    @Test
    void getUnauthenticatedRequestToAddPlayer_shouldTrowException() throws Exception {
        AuthenticationMetaData principal = principal(UUID.randomUUID());
        principal.setRole(USER);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/admin-panel/players/new")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(view().name("not-found"));
    }

    @Test
    void getAuthenticatedRequestToAddPlayer_shouldRedirect() throws Exception {
        AuthenticationMetaData principal = principal(UUID.randomUUID());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/admin-panel/players/new")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("add-player"))
                .andExpect(model().attributeExists("addPlayerRequest", "clubs", "capNumbers"));
    }


    private AuthenticationMetaData principal(UUID userId){
        return new AuthenticationMetaData( userId,
                "nik123",
                "123123",
                "admin@gmail.com",
                ADMIN,
                LocalDateTime.now());
    }
}
