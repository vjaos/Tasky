package org.tasky.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.service.impl.ProjectServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ProjectServiceTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    public void setup() {
        initMocks(this);
        initUser();
    }

    private User user;

    @Test
    public void shouldSaveProject() {
        Project project = new Project();
        project.setName("Tasky");
        project.setDescription("Descr");
        project.setOwner(user);

        projectService.save(project);

        verify(projectRepository, times(1)).save(project);
    }


    @Test
    public void whenProjectAlreadyExists_thenReturnFalse() {
        Project project = new Project();
        project.setName("Tasky");
        project.setDescription("Descr");
        project.setOwner(user);

        doReturn(Optional.of(new Project())).when(projectRepository).findProjectByName(project.getName());

        assertThrows(IllegalArgumentException.class, () -> projectService.save(project));

    }

    private void initUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test123");
        user.setEmail("test@test.com");
        this.user = user;
    }
}