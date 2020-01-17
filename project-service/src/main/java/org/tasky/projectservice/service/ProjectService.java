package org.tasky.projectservice.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tasky.projectservice.entity.Project;
import org.tasky.projectservice.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository repository) {
        this.projectRepository = repository;
    }

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

}
