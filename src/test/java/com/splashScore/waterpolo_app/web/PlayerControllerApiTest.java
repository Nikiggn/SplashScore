package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.player.service.PlayerService;
import com.splashScore.waterpolo_app.referee.service.RefereeService;
import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.web.dto.AddPlayerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.splashScore.waterpolo_app.user.model.UserRole.ADMIN;
import static com.splashScore.waterpolo_app.user.model.UserRole.USER;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
public class PlayerControllerApiTest {

    @MockitoBean
    private PlayerService playerService;

    @MockitoBean
    private ClubService clubService;

    @Autowired
    MockMvc mockMvc;


    @Test
    void putInvalidPlayerId_whenChangingPlayerStatus_thenThrowsException() throws Exception {
        AuthenticationMetaData principal =  principal(UUID.randomUUID());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/players/{id}/status", UUID.randomUUID())
                .with(user(principal))
                .with(csrf());

        // 2. Send Request
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(view().name("not-found"));
    }

    @Test
    void putAuthorizedRequestToChangePlayerStatus_whenChangingPlayerStatus_thenShouldChangeStatuesAndRedirects() throws Exception {
        AuthenticationMetaData principal =  principal(UUID.randomUUID());
        principal.setRole(ADMIN);

        UUID RandomPlayerId = UUID.randomUUID();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/players/{id}/status", RandomPlayerId)
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-panel?activeDiv=players"));

        verify(playerService, times(1)).changePlayerStatus(RandomPlayerId);
    }

    @Test
    void shouldRedirectToAddPlayerWhenFormValidationFails() throws Exception {
        AuthenticationMetaData principal =  principal(UUID.randomUUID());
        principal.setRole(ADMIN);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/players")
                .formField("fullName", "")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("add-player"))
                .andExpect(model().attributeExists("addPlayerRequest", "clubs", "capNumbers"));

        verify(playerService, never()).saveNewPlayer(any(AddPlayerRequest.class));
    }

    @Test
    void shouldRedirectToAdminPanelWhenFormValidationGoesThrough() throws Exception {
        AuthenticationMetaData principal =  principal(UUID.randomUUID());
        principal.setRole(ADMIN);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/players")
                .formField("fullName", "Nikola Antoniev")
                .formField("dateOfBirth", "1999-12-02")
                .formField("clubId", UUID.randomUUID().toString())
                .formField("country", "BULGARIA")
                .formField("capNumber", "1")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-panel?activeDiv=players"));

        verify(playerService).saveNewPlayer(any(AddPlayerRequest.class));
    }

    private AuthenticationMetaData principal(UUID userId){
        return new AuthenticationMetaData( userId,
                "nik123",
                "123123",
                "admin@gmail.com",
                USER,
                LocalDateTime.now());
    }
}
