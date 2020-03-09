package org.tasky.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.tasky.backend.dto.request.ProjectRequest;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;

import java.util.Optional;

@Service
public class ProjectService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProjectRepository projectRepository;


    public void save(@NonNull ProjectRequest request, @NonNull User user) throws IllegalArgumentException {

        Optional<Project> existing = projectRepository.findProjectByName(request.getProjectName());
        existing.ifPresent(it -> {
            throw new IllegalArgumentException("Project already exists: " + request.getProjectName());
        });

        Project project = new Project();

        project.setName(request.getProjectName());
        project.setDescription(request.getProjectDescription());
        project.setUser(user);

        projectRepository.save(project);
        logger.info("Project successfully created: " + request.getProjectName());
    }


    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

}
