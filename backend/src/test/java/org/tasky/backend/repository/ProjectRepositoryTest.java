package org.tasky.backend.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tasky.backend.entity.Project;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void saveAndFindProjectByName() {
        Project project = new Project();
        project.setName("Tasky");

        projectRepository.save(project);
        Optional<Project> found = projectRepository.findProjectByName(project.getName());

        assertTrue(found.isPresent());
        assertEquals(found.get().getName(), project.getName());
    }

}