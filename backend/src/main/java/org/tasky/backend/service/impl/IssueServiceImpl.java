package org.tasky.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.IssueRepository;
import org.tasky.backend.service.IssueService;
import org.tasky.backend.service.ProjectService;
import org.tasky.backend.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class IssueServiceImpl implements IssueService {


    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;


    @Override
    public List<Issue> getIssuesByProjectId(Long projectId) {
        Project project = projectService.findProjectById(projectId);
        return project.getIssues();
    }

    @Override
    public void createIssue(Issue issueData, Long projectId, String username) throws EntityNotFoundException {
        Project project = projectService.findProjectById(projectId);
        User user = userService.findUserByUsername(username);

        Issue issue = new Issue();
        issue.setAuthor(user);
        issue.setProject(project);
        issue.setTitle(issueData.getTitle());
        issue.setDescription(issueData.getDescription());

        issueRepository.save(issue);
    }

}
