package org.tasky.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tasky.backend.dto.request.IssueRequest;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.repository.IssueRepository;
import org.tasky.backend.service.impl.IssueServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class IssueServiceTest {

    @InjectMocks
    private IssueServiceImpl issueServiceImpl;

    @Mock
    private IssueRepository issueRepository;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldCreateNewIssue() {
        Issue issue = new Issue();

        IssueRequest request = new IssueRequest();

        issue.setTitle("Test issue");
        issue.setDescription("Test test test");

        Project project = new Project();
        issue.setProject(project);

        issueServiceImpl.save(issue);

        verify(issueRepository, times(1))
                .save(ArgumentMatchers.any(Issue.class));
    }


}