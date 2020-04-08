package org.tasky.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.repository.UserRepository;
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
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        initMocks(this);
    }


    @Test
    public void shouldCreateProject() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));
        when(projectRepository.save(any(Project.class))).thenReturn(new Project());

        projectService.createProject(new Project(), "Username");

        verify(projectRepository, times(1)).save(any(Project.class));
    }


    @Test
    public void shouldUpdateProjectInformation() {

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new Project()));
        when(projectRepository.save(any(Project.class))).thenReturn(new Project());

        projectService.updateProject(new Project(), 1L);

        verify(
                projectRepository,
                times(1)
        ).save(any(Project.class));
    }

    @Test
    public void whenProjectNotFound_thenThrowEntityNotFoundException() {
        assertThrows(
                EntityNotFoundException.class,
                () -> projectService.updateProject(new Project(), 1L)
        );
    }


}