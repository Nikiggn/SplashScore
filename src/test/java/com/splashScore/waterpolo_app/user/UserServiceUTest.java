package com.splashScore.waterpolo_app.user;

import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.exception.EmailAlreadyExistException;
import com.splashScore.waterpolo_app.exception.UsernameAlreadyExistException;
import com.splashScore.waterpolo_app.security.AuthenticationMetaData;
import com.splashScore.waterpolo_app.user.model.User;
import com.splashScore.waterpolo_app.user.model.UserRole;
import com.splashScore.waterpolo_app.user.repository.UserRepository;
import com.splashScore.waterpolo_app.user.service.UserService;
import com.splashScore.waterpolo_app.web.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUTest {

    @Mock //имам контрол над dependency-to
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void givenSameIds_whenAdminSwitchesUsersRole_thenExceptionIsThrown() {
        UUID id = UUID.randomUUID();
        assertThrows(DomainException.class, () -> userService.changeUserRole(id, id));
    }

    @Test
    void givenMissingUserFromDB_whenAdminSwitchesUsersRole_thenExceptionIsThrown() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(DomainException.class, () -> userService.changeUserRole(id, any()));
    }

    @Test
    void givenUserWithRoleAdmin_whenChangeUserRole_thenReceivesUserRole(){
        UUID id = UUID.randomUUID();

        User user = new User();
        user.setId(id);
        user.setRole(UserRole.ADMIN);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.changeUserRole(id, any());
        assertThat(user.getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    void givenUserWithRoleUser_whenChangeUserRole_thenReceivesAdminRole(){
        UUID id = UUID.randomUUID();

        User user = new User();
        user.setId(id);
        user.setRole(UserRole.USER);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.changeUserRole(id, any());
        assertThat(user.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    void givenValidUserId_whenFetchingUser_thenReturnsCorrectUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(id);

        assertThat(retrievedUser.getId()).isEqualTo(user.getId());
    }
    @Test
    void givenInvalidUserID_whenGettingFromTheDB_thenExceptionIsThrown(){
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> userService.getUserById(any()));
    }

    @Test
    void givenUserAdmin_whenRetrievingAllUsersAsAdmin_thenReturnsFilteredUsers() {
        UUID adminId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        User admin = new User();
        admin.setId(adminId);

        User user = new User();
        user.setId(userId);

        List<User> allUsers = List.of(admin, user);

        when(userRepository.findAll()).thenReturn(allUsers);

        List<User> filteredUsers = userService.getAllUsers(admin);

        assertThat(filteredUsers.getFirst()).isEqualTo(user);
    }

    @Test
    void givenRegisterRequest_whenRegisterUser_thenRegisterCorrectly() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email@email.com");
        registerRequest.setPassword("password");
        registerRequest.setUsername("nik123");

        User expectedUser = new User();
        expectedUser.setUsername("nik123");
        expectedUser.setEmail("email@email.com");
        expectedUser.setPassword("encodedPassword");
        expectedUser.setRole(UserRole.USER);
        expectedUser.setCreatedOn(LocalDateTime.now());

        when(userRepository.findByUsername(registerRequest.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(modelMapper.map(registerRequest, User.class)).thenReturn(expectedUser);
        when(userRepository.findAll()).thenReturn(List.of());
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User actualUser = userService.register(registerRequest);

        // Assert
        assertThat(actualUser.getUsername()).isEqualTo(registerRequest.getUsername());
        assertThat(actualUser.getEmail()).isEqualTo(registerRequest.getEmail());
        assertThat(actualUser.getPassword()).isEqualTo("encodedPassword");
        assertThat(actualUser.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    void givenRegisterRequest_whenRegisteringUser_thenThrowsDomainException_whenUsernameAlreadyExists() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("nik123");

        User user = new User();
        user.setUsername("nik123");

        when(userRepository.findByUsername(registerRequest.getUsername())).thenReturn(Optional.of(user));
        assertThrows(UsernameAlreadyExistException.class, () -> userService.register(registerRequest));
    }

    @Test
    void givenRegisterRequest_whenRegisteringUser_thenThrowsDomainException_whenEmailAlreadyExists() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email@email.com");

        User user = new User();
        user.setEmail("email@email.com");

        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(user));
        assertThrows(EmailAlreadyExistException.class, () -> userService.register(registerRequest));
    }

    @Test
    void givenExistingUsername_whenLoadingUserByUsername_thenReturnsUserDetails() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("nik123");
        user.setEmail("email@email.com");
        user.setPassword("encodedPassword");
        user.setRole(UserRole.USER);

        when(userRepository.findByUsername("nik123")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("nik123");

        assertThat(userDetails)
                .isInstanceOf(AuthenticationMetaData.class)
                .extracting("id", "username", "password", "email", "role")
                .containsExactly(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
    }

    @Test
    void givenInvalidUsername_whenLoadingUserByUsername_thenThrowsException() {
        when(userRepository.findByUsername("nik123")).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> userService.loadUserByUsername("nik123"));
    }
}
