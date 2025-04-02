package com.splashScore.waterpolo_app;

import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.repository.UserRepository;
import com.splashScore.waterpolo_app.user.service.UserService;
import com.splashScore.waterpolo_app.web.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.splashScore.waterpolo_app.user.model.UserRole.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RegisteringUserITest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void registeringUser_happyPath() {
        RegisterRequest registerRequest = new RegisterRequest("nik123", "nik123@gmail.com", "123456");
        User testUser = userService.register(registerRequest);
        assertNotNull(testUser);

        Optional<User> realUser = userRepository.findById(testUser.getId());
        assertTrue(realUser.isPresent());
        assertEquals(testUser.getId(), realUser.get().getId());
        assertEquals(testUser.getEmail(), realUser.get().getEmail());
        assertEquals(testUser.getPassword(), realUser.get().getPassword());
        assertEquals(ADMIN, realUser.get().getRole());

        assertEquals(1, userRepository.findAll().size());
    }
}
