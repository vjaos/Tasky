package org.tasky.backend.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tasky.backend.TestUtils;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;
    private User user;

    @BeforeEach
    public void inits() {
        saveUsertoDb();
    }

    @Test
    public void saveAndFindProjectByUserAndName() {
        Project project = new Project();
        project.setName("Tasky");
        project.setDescription("Description");
        project.setOwner(user);

        projectRepository.save(project);
        Optional<Project> found = projectRepository.findProjectByName(project.getName());

        assertTrue(found.isPresent());
        assertEquals(found.get().getName(), project.getName());
        assertEquals(user.getUsername(), project.getOwner().getUsername());
    }


    private void saveUsertoDb() {
        User userToSave = TestUtils.initUser();
        this.user = userRepository.save(userToSave);
    }

}