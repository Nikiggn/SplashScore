package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.exception.EmailAlreadyExistException;
import com.splashScore.waterpolo_app.exception.UsernameAlreadyExistException;
import com.splashScore.waterpolo_app.match.service.MatchService;
import com.splashScore.waterpolo_app.player.service.PlayerService;
import com.splashScore.waterpolo_app.referee.service.RefereeService;
import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.splashScore.waterpolo_app.user.model.UserRole.ADMIN;
import static com.splashScore.waterpolo_app.user.model.UserRole.USER;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IndexController.class)
public class IndexControllerApiTest {
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private PlayerService playerService;
    @MockitoBean
    private ClubService clubService;
    @MockitoBean
    private RefereeService refereeService;
    @MockitoBean
    private MatchService matchService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRequestToIndexEndpoint_shouldReturnIndexView() throws Exception {
        User testUser = testUser();
        UUID userId = UUID.randomUUID();
        AuthenticationMetaData principal = principal(userId);

        // Build Request
        MockHttpServletRequestBuilder request = get("/")
                .with(user(principal));

        when(userService.getUserById(userId)).thenReturn(testUser);


        //Send Request
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("user"));

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getRequestToRegisterEndpoint_shouldReturnRegisterView() throws Exception {
        MockHttpServletRequestBuilder request = get("/register");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registerRequest")); // Check if 'user' exists in the model
    }

    @Test
    void postRequestToRegisterEndpoint_happyPath() throws Exception {
        MockHttpServletRequestBuilder request = post("/register")
                .formField("username", "nik123")
                .formField("email", "nik123@gmail.com")
                .formField("password", "123123")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).register(any());
    }

    @Test
    void postRequestToRegisterEndpointWithInvalidData_returnRegisterView() throws Exception {
        MockHttpServletRequestBuilder request = post("/register")
                .formField("username", "")
                .formField("email", "")
                .formField("password", "123123")
                .with(csrf());


        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
        verify(userService, never()).register(any());
    }

    @Test
    void postRequestToRegisterEndpointWhenUsernameAlreadyExist_thenRedirectToRegisterWithFlashParameter() throws Exception {
        when(userService.register(any())).thenThrow(new UsernameAlreadyExistException("Username already exist!"));

        MockHttpServletRequestBuilder request = post("/register")
                .formField("username", "nik123")
                .formField("email", "nik123@gmail.com")
                .formField("password", "123123")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"))
                .andExpect(flash().attributeExists("usernameAlreadyExistMessage"));
        verify(userService, times(1)).register(any());
    }

    @Test
    void postRequestToRegisterEndpointWhenEmailAlreadyExist_thenRedirectToRegisterWithFlashParameter() throws Exception {
        when(userService.register(any())).thenThrow(new EmailAlreadyExistException("Email already exist!"));

        MockHttpServletRequestBuilder request = post("/register")
                .formField("username", "nik123")
                .formField("email", "nik123@gmail.com")
                .formField("password", "123123")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"))
                .andExpect(flash().attributeExists("emailAlreadyExistMessage"));
        verify(userService, times(1)).register(any());
    }

    @Test
    void getRequestToLoginEndpointWithErrorParam_shouldReturnLoginViewAndErrorMessage() throws Exception {
        MockHttpServletRequestBuilder request = get("/login").param("error", "");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginRequest", "errorMessage"));
    }

    @Test
    void getUnauthenticatedRequestToAdminPanelPage_shouldReturnNotFoundView() throws Exception {
        MockHttpServletRequestBuilder request = get("/admin-panel");
        mockMvc.perform(request).andExpect(status().is3xxRedirection());
        verify(userService, never()).getUserById(any());
    }

    @Test
    void getAuthenticatedRequestToAdminPanelPage_shouldReturnAdminPage() throws Exception {
        User testUser = testUser();

        when(userService.getUserById(any())).thenReturn(testUser);
        when(playerService.getAllPlayers()).thenReturn(List.of());
        when(clubService.getAllClubs()).thenReturn(List.of());
        when(userService.getAllUsers(testUser)).thenReturn(List.of());
        when(refereeService.getAllReferees()).thenReturn(List.of());
        when(matchService.getAllMatchesWithClubDetails()).thenReturn(List.of());

        UUID userId = UUID.randomUUID();
        AuthenticationMetaData principal = principal(userId);

        MockHttpServletRequestBuilder request = get("/admin-panel")
                .with(user(principal));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-panel"))
                .andExpect(model().attributeExists("user", "players", "users", "clubs", "referees", "matches"));

        verify(userService, times(1)).getUserById(userId);
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

    private AuthenticationMetaData principal(UUID userId) {
        return new AuthenticationMetaData(userId,
                "nik123",
                "123123",
                "admin@gmail.com",
                ADMIN,
                LocalDateTime.now());
    }
}
