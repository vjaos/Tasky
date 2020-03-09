package org.tasky.backend.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tasky.backend.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveAndFindUserByName() {
        User user = new User();

        user.setUsername("Test");
        user.setPassword("password");
        userRepository.save(user);

        Optional<User> found = userRepository.findUserByUsername(user.getUsername());

        assertTrue(found.isPresent());
        assertEquals(user.getUsername(), found.get().getUsername());
    }


}