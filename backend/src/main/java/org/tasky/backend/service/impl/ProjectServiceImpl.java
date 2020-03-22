package org.tasky.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.service.ProjectService;
import org.tasky.backend.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private UserService userService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public Optional<Project> save(Project project, String username) {
        Optional<Project> existing = projectRepository.findProjectByName(project.getName());
        existing.ifPresent(it -> {
            throw new IllegalArgumentException("Project already exists: " + it.getName());
        });

        User user = userService.findUserByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("User with username %s not found", username)
                ));

        Project projectInstance = new Project();
        projectInstance.setName(project.getName());
        projectInstance.setDescription(project.getDescription());
        projectInstance.setOwner(user);

        return Optional.ofNullable(projectRepository.save(projectInstance));
    }

    @Override
    public Optional<Project> updateProject(Project projectData, Long projectId) {
        Project projectFromDb = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Project with id %d not found", projectId)));

        projectFromDb.setName(projectData.getName());
        projectFromDb.setDescription(projectData.getDescription());

        return Optional.ofNullable(projectRepository.save(projectFromDb));
    }


}
