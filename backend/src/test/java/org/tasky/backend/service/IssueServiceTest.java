package org.tasky.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tasky.backend.TestUtils;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.IssueRepository;
import org.tasky.backend.service.impl.IssueServiceImpl;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class IssueServiceTest {

    @InjectMocks
    private IssueServiceImpl issueServiceImpl;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    private User user;
    private Project project;
    private Issue issue;

    @BeforeEach
    public void setup() {
        initMocks(this);
        this.user = TestUtils.initUser();
        this.project = TestUtils.initProject(user);
        this.issue = TestUtils.initIssue(user, project);
    }

    @Test
    public void shouldCreateNewIssue() {
        doReturn(project)
                .when(projectService)
                .findProjectById(project.getId());

        doReturn(new User())
                .when(userService)
                .findUserByUsername(user.getUsername());


        issueServiceImpl.createIssue(issue, project.getId(), user.getUsername());

        verify(
                issueRepository,
                times(1)
        ).save(ArgumentMatchers.any(Issue.class));
    }
}