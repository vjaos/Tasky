package org.tasky.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.dto.request.ProjectCreationRequest;
import org.tasky.backend.repository.ProjectRepository;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public void createProject(ProjectCreationRequest projectCreationRequest, User user) {
        Project project = new Project();

        project.setName(projectCreationRequest.getProjectName());
        project.setDescription(projectCreationRequest.getProjectDescription());
        project.setUser(user);

        projectRepository.save(project);
    }


    public boolean isProjectExist(String name) {
        return projectRepository.existsByName(name);
    }

    public Optional<Project> findProject(@NonNull String name) {
        return projectRepository.findProjectByName(name);
    }

    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

    public void updateProject(ProjectCreationRequest project) {
        Optional<Project> projectOptional =
                projectRepository.findProjectByName(project.getProjectName());

        if (projectOptional.isPresent()) {

            Project projectFromDb = projectOptional.get();
            projectFromDb.setName(project.getProjectName());
            projectFromDb.setDescription(project.getProjectDescription());

            projectRepository.save(projectFromDb);
        }
    }


}
