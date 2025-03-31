package com.splashScore.waterpolo_app.web;

import com.splashScore.waterpolo_app.referee.service.RefereeService;
import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.web.dto.AddRefereeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.splashScore.waterpolo_app.user.model.UserRole.ADMIN;
import static com.splashScore.waterpolo_app.user.model.UserRole.USER;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RefereeController.class)
public class RefereeControllerApiTest {
    @MockitoBean
    private RefereeService refereeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void putInvalidRefereeId_whenChangingRefereeStatus_thenThrowsException() throws Exception {
        AuthenticationMetaData principal =  principal(UUID.randomUUID());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/referees/{id}/status", UUID.randomUUID())
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(view().name("not-found"));

        verify(refereeService, never()).changeRefereeStatus(UUID.randomUUID());
    }

    @Test
    void putAuthorizedRequest_whenChangingRefereeStatus() throws Exception {
        AuthenticationMetaData principal =  principal(UUID.randomUUID());
        principal.setRole(ADMIN);

        UUID refereeId = UUID.randomUUID();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/referees/{id}/status", refereeId)
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-panel?activeDiv=referees"));

        verify(refereeService, times(1)).changeRefereeStatus(refereeId);
    }

    @Test
    void shouldRedirectToAddRefereeWhenFormValidationFails() throws Exception{
        AuthenticationMetaData principal =  principal(UUID.randomUUID());
        principal.setRole(ADMIN);

         MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.post("/referees")
                 .formField("fullName", "")
                 .with(user(principal))
                 .with(csrf());

         mockMvc.perform(request)
                 .andExpect(status().isOk())
                 .andExpect(view().name("add-referee"))
                 .andExpect(model().attributeExists("addRefereeRequest"));

         verify(refereeService, never()).saveNewReferee(any(AddRefereeRequest.class));

    }


    @Test
    void shouldRedirectToAdminPanelWhenFormValidationGoesThrough() throws Exception{
        AuthenticationMetaData principal =  principal(UUID.randomUUID());
        principal.setRole(ADMIN);

        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.post("/referees")
                .formField("fullName", "Ivan Ivanov")
                .formField("country", "BULGARIA")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-panel?activeDiv=referees"));

        verify(refereeService, times(1)).saveNewReferee(any(AddRefereeRequest.class));
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
