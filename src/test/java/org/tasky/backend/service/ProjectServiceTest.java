package org.tasky.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tasky.backend.TestUtils;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.service.impl.ProjectServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ProjectServiceTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        initMocks(this);
        this.user = TestUtils.initUser();
        this.project = TestUtils.initProject(user);
    }

    private User user;
    private Project project;

    @Test
    public void shouldCreateProject() {
        doReturn(user)
                .when(userService)
                .findUserByUsername(user.getUsername());

        projectService.createProject(project, user.getUsername());

        verify(projectRepository, times(1)).save(project);
    }


    @Test
    public void whenProjectAlreadyExists_thenThrowIllegalArgumentException() {

        doReturn(Optional.of(new Project()))
                .when(projectRepository)
                .findProjectByName(project.getName());

        assertThrows(
                IllegalArgumentException.class,
                () -> projectService.createProject(project, user.getUsername())
        );
    }


    @Test
    public void shouldUpdateProjectInformation() {
        doReturn(Optional.of(project))
                .when(projectRepository)
                .findById(project.getId());

        projectService.updateProject(project, project.getId());

        verify(
                projectRepository,
                times(1)
        ).save(project);
    }

    @Test
    public void whenProjectNotFound_thenThrowEntityNotFoundException() {
        assertThrows(
                EntityNotFoundException.class,
                () -> projectService.updateProject(new Project(), 1L)
        );
    }


}