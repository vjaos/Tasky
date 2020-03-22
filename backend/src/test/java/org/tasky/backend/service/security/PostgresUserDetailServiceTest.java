package org.tasky.backend.service.security;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class PostgresUserDetailServiceTest {

    @Autowired
    private PostgresUserDetailService userDetailService;

    @Test
    @Sql(value = {"/import-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/import-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void whenUserExists_thenLoadByUsername() {
        String username = "jaje";
        UserDetails loadedUser = userDetailService.loadUserByUsername(username);
        assertEquals(username, loadedUser.getUsername());
    }

    @Test
    public void whenUserNotExists_thenThrowException() {
        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername("name"));
    }
}