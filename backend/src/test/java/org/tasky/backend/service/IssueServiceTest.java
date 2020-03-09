package org.tasky.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tasky.backend.dto.request.IssueRequest;
import org.tasky.backend.dto.request.ProjectRequest;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.IssueRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class IssueServiceTest {

    @InjectMocks
    private IssueService issueService;

    @Mock
    private IssueRepository issueRepository;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldCreateNewIssue() {
        IssueRequest request = new IssueRequest();

        request.setTitle("Test issue");
        request.setDescription("Test test test");

        Project project = new Project();

        issueService.createIssue(project, request);

        verify(issueRepository, times(1))
                .save(ArgumentMatchers.any(Issue.class));
    }

    @Test
    public void whenProjectAlreadyContainsIssue_thenFail() {
        IssueRequest request = new IssueRequest();

        request.setTitle("Test issue");
        request.setDescription("Test test test");

        Issue issue = new Issue();
        issue.setTitle("Test issue");

        Project project = new Project();
        project.getIssues().add(issue);

        assertThrows(IllegalArgumentException.class, () -> issueService.createIssue(project, request));
    }

}