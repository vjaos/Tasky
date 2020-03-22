package org.tasky.backend.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.repository.RoleRepository;
import org.tasky.backend.repository.UserRepository;
import org.tasky.backend.service.impl.UserServiceImpl;

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
        initUser();
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
                .findByUsername("test");

        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void shouldDeleteUser() {
        userService.deleteUserById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void shouldReturnProjectByUserAndProjectId() {
        Optional<Project> project = userService.getProjectByUserAndProjectId(user, 1L);
        verify(projectRepository, times(1)).findProjectByOwnerAndId(user, 1L);
    }


    private void initUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test123");
        user.setEmail("test@test.com");
        user.setFirstName("Bla");
        user.setLastName("bla");
        this.user = user;
    }
}
