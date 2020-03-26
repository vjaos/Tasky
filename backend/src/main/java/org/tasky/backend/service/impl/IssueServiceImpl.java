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
import java.util.Optional;

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
    public List<Issue> getIssuesByProjectId(Long projectId) throws EntityNotFoundException {
        Project project = projectService.findProjectById(projectId);
        return project.getIssues();
    }

    @Override
    public Optional<Issue> getOne(Long issueId, Long projectId) throws EntityNotFoundException {
        Project project = projectService.findProjectById(projectId);
        return issueRepository.findByIdAndProject(issueId, project);
    }

    @Override
    public Optional<Issue> updateIssue(Long issueId, Issue issueData) {
        Issue issueFromDb = issueRepository.findById(issueId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Isse with id %d not found", issueId))
                );

        issueFromDb.setTitle(issueData.getTitle());
        issueFromDb.setDescription(issueData.getDescription());
        issueFromDb.setIssueStatus(issueData.getIssueStatus());

        return Optional.ofNullable(issueRepository.save(issueFromDb));
    }

    @Override
    public Optional<Issue> createIssue(Issue issueData, Long projectId, String username) throws EntityNotFoundException {
        Project project = projectService.findProjectById(projectId);
        User user = userService.findUserByUsername(username);

        Issue issue = new Issue();
        issue.setAuthor(user);
        issue.setProject(project);
        issue.setTitle(issueData.getTitle());
        issue.setDescription(issueData.getDescription());

        return Optional.ofNullable(issueRepository.save(issue));
    }

}
