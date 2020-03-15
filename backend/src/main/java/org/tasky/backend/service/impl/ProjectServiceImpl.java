package org.tasky.backend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.service.ProjectService;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public Project save(Project project) {
        Optional<Project> existing = projectRepository.findProjectByName(project.getName());
        existing.ifPresent(it -> {
            throw new IllegalArgumentException("Project already exists: " + it.getName());
        });
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> getProjectByUserAndProjectId(User user, Long id) {
        return Optional.empty();
    }


}
