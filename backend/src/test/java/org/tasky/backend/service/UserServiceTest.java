package org.tasky.backend.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tasky.backend.dto.request.SignUpRequest;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.RoleRepository;
import org.tasky.backend.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    private SignUpRequest request;

    @BeforeEach
    public void setup() {
        initMocks(this);
        this.request = createRequest();
    }


    private SignUpRequest createRequest() {
        SignUpRequest request = new SignUpRequest();

        request.setUsername("TestUser");
        request.setEmail("test@test.com");
        request.setFirstName("Test");
        request.setLastName("Test");
        request.setPassword("test123");
        return request;
    }

    @Test
    public void shouldCreateUser() {
        userService.createUser(request);
        verify(userRepository, times(1)).save(ArgumentMatchers.any(User.class));
    }


    @Test
    public void whenUserAlreadyExists_thenFail() {
        doReturn(true)
                .when(userRepository)
                .existsUserByUsername("TestUser");

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(request));
    }

}
