package org.tasky.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.tasky.backend.dto.request.IssueRequest;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.repository.IssueRepository;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;


    public void createIssue(@NonNull Project project, @NonNull IssueRequest issueRequest) {

        if (project.containsIssue(issueRequest.getTitle())) {
            throw new IllegalArgumentException("Issue already exists! " + issueRequest.getTitle());
        }

        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setProject(project);

        issueRepository.save(issue);
    }

}
