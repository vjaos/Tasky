package org.tasky.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.tasky.backend.dto.request.ProjectCreationRequest;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.repository.IssueRepository;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private IssueRepository issueRepository;

    public void save(ProjectCreationRequest projectCreationRequest, User user) {
        Project project = new Project();

        project.setName(projectCreationRequest.getProjectName());
        project.setDescription(projectCreationRequest.getProjectDescription());
        project.setUser(user);

        projectRepository.save(project);
    }


    public boolean isProjectExist(String name) {
        return projectRepository.existsByName(name);
    }

    public boolean containsIssue(String issueTitle, Project project) {
        return issueRepository.existsByTitleAndProject(issueTitle, project);
    }

    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

}
