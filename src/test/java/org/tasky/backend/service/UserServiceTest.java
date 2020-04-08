package org.tasky.backend.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tasky.backend.TestUtils;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.repository.RoleRepository;
import org.tasky.backend.repository.UserRepository;
import org.tasky.backend.service.impl.UserServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    private User user;

    @BeforeEach
    public void setup() {
        initMocks(this);
        this.user = TestUtils.initUser();
    }


    @Test
    public void shouldCreateUser() {
        userService.saveUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void whenUserAlreadyExists_thenFail() {
        doReturn(Optional.of(new User()))
                .when(userRepository)
                .findByUsername(user.getUsername());

        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void shouldDeleteUser() {
        userService.deleteUserById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void whenUserNotFound_thenThrowEntityNotFoundException() {
        assertThrows(
                EntityNotFoundException.class,
                () -> userService.findUserByUsername(user.getUsername())
        );
    }



}
