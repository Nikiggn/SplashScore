package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.service.UserService;
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
 import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static reactor.core.publisher.Mono.never;

@WebMvcTest(UserController.class)
public class UserControllerApiTest {
    @MockitoBean
    private UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getUnauthenticatedRequestToUsersProfile_shouldRedirect() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/users/profile")
                 .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection());
     }

    @Test
    void shouldChangeUserRoleSuccessfully() throws Exception {
        UUID uuid = UUID.randomUUID();
        AuthenticationMetaData principal = principal(uuid);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/users/{id}/role", uuid)
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection()) // Ensure redirect happens
                .andExpect(redirectedUrl("/admin-panel?activeDiv=users")); // Ensure correct redirect

        verify(userService, times(1)).changeUserRole(eq(uuid), eq(principal.getId()));
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
