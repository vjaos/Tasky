package org.tasky.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.repository.IssueRepository;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.repository.UserRepository;
import org.tasky.backend.service.IssueService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    public static final String ISSUE_NOT_FOUND = "Issue with id '%d' not found";


    private IssueRepository issueRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository,
                            UserRepository userRepository,
                            ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Issue> getIssuesByProjectId(Long projectId) throws EntityNotFoundException {
        return projectRepository.findById(projectId)
                .map(Project::getIssues)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(ProjectServiceImpl.PROJECT_NOT_FOUND, projectId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Issue> getOne(Long issueId) {
        return issueRepository.findById(issueId);
    }

    @Override
    @Transactional
    public Issue updateIssue(Long issueId, Issue issueData) throws EntityNotFoundException {
        return issueRepository.findById(issueId)
                .map(issue -> {
                    issue.setDescription(issueData.getDescription());
                    issue.setTitle(issueData.getTitle());
                    issue.setIssueStatus(issueData.getIssueStatus());
                    return issueRepository.save(issue);
                })
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(ISSUE_NOT_FOUND, issueId)));
    }

    @Override
    @Transactional
    public Issue createIssue(Issue issueData, Long projectId, String username) throws EntityNotFoundException {

        return userRepository.findByUsername(username)
                .map(user -> projectRepository.findById(projectId)
                        .map(project -> {
                            issueData.setAuthor(user);
                            issueData.setProject(project);
                            return issueRepository.save(issueData);
                        })
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        String.format(ProjectServiceImpl.PROJECT_NOT_FOUND, projectId))))
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(UserServiceImpl.USER_NOT_FOUND, username)));
    }

}
