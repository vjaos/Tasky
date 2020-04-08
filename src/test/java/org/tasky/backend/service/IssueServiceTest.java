package org.tasky.backend.service;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.IssueRepository;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.repository.UserRepository;
import org.tasky.backend.service.impl.IssueServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@Ignore
class IssueServiceTest {

    @InjectMocks
    private IssueServiceImpl issueServiceImpl;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private Project project;
    private Issue issue;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldCreateNewIssue() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new Project()));
        when(issueRepository.save(any(Issue.class))).thenReturn(new Issue());

        issueServiceImpl.createIssue(new Issue(), 1L, "Username");

        verify(
                issueRepository,
                times(1)
        ).save(any(Issue.class));
    }
}