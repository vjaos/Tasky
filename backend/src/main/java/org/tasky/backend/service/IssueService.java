package org.tasky.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tasky.backend.dto.request.IssueRequest;
import org.tasky.backend.entity.*;
import org.tasky.backend.repository.IssueRepository;
import org.tasky.backend.repository.ProjectRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public void createIssue(Project project, IssueRequest issueData) {
        Issue issue = new Issue();


        issue.setTitle(issueData.getTitle());
        issue.setDescription(issueData.getDescription());
        issue.setProject(project);

        issueRepository.save(issue);
    }

}
