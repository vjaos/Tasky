package org.tasky.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tasky.backend.dto.request.ProjectRequest;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.IssueRepository;
import org.tasky.backend.repository.ProjectRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private IssueRepository issueRepository;
    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }


    @Test
    public void shouldCreateProject() {
        ProjectRequest request = new ProjectRequest();

        request.setProjectName("Test Project");
        request.setProjectDescription("Test test test");

        User user = new User();

        projectService.save(request, user);

        verify(projectRepository, times(1))
                .save(ArgumentMatchers.any(Project.class));
    }


    @Test
    public void whenProjectAlreadyExists_thenReturnFalse() {
        ProjectRequest request = new ProjectRequest();
        request.setProjectName("Tasky");
        User user = new User();

        doReturn(Optional.of(new Project())).when(projectRepository)
                .findProjectByName(request.getProjectName());
        assertThrows(IllegalArgumentException.class, () -> projectService.save(request, user));
    }
}