package org.tasky.backend.service.security;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;


class PostgresUserDetailServiceTest {

    @InjectMocks
    private PostgresUserDetailService userDetailService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }


    @Test
    public void whenUserExists_thenLoadByUsername() {
        User user = new User();

        doReturn(Optional.of(user))
                .when(userRepository)
                .findByUsername("name");

        UserDetails loadedUser = userDetailService.loadUserByUsername("name");
        assertEquals(user, loadedUser);
    }

    @Test
    public void whenUserNotExists_thenThrowException() {
        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername("name"));
    }
}