package org.tasky.backend.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tasky.backend.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveAndFindUserByName() {
        User user = new User();

        user.setUsername("Test1");
        user.setFirstName("first");
        user.setLastName("last");
        user.setPassword("password");
        user.setEmail("test@mail.test");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername(user.getUsername());

        assertTrue(found.isPresent());
        assertEquals(user.getUsername(), found.get().getUsername());
    }


}