package org.tasky.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.service.ProjectService;
import org.tasky.backend.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private UserService userService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    /**
     * Create a project according to given data
     *
     * @param projectData entity contains data for project creation
     * @param username    of user which create a project
     * @return Optional<Project>
     * @throws EntityNotFoundException  When user not found
     * @throws IllegalArgumentException If with given name project already exists
     */
    @Override
    public Optional<Project> createProject(Project projectData, String username)
            throws EntityNotFoundException, IllegalArgumentException {

        Optional<Project> existing = projectRepository.findProjectByName(projectData.getName());

        existing.ifPresent(it -> {
            throw new IllegalArgumentException("Project already exists: " + it.getName());
        });

        final User user = userService.findUserByUsername(username);

        Project projectInstance = new Project();
        projectInstance.setName(projectData.getName());
        projectInstance.setDescription(projectData.getDescription());
        projectInstance.setOwner(user);

        return Optional.ofNullable(projectRepository.save(projectInstance));
    }

    /**
     * Update project according to given data
     *
     * @param projectData for project that will be updated
     * @param projectId   of project to be updated
     * @return Optional<Project> updated project
     * @throws EntityNotFoundException
     */
    @Override
    public Optional<Project> updateProject(Project projectData, Long projectId) throws EntityNotFoundException {
        Project projectFromDb = findProjectById(projectId);

        projectFromDb.setName(projectData.getName());
        projectFromDb.setDescription(projectData.getDescription());

        return Optional.ofNullable(projectRepository.save(projectFromDb));
    }

    /**
     * Looking for a project according to given Id
     *
     * @param id of project to be found
     * @return found project
     * @throws EntityNotFoundException if there isn't such project
     */
    @Override
    public Project findProjectById(Long id) throws EntityNotFoundException {
        return projectRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Project with id %d not found", id))
                );
    }

    @Override
    public List<Project> getAllUserProject(String username) {
        User user = userService.findUserByUsername(username);
        return projectRepository.findAllByOwner(user);
    }

}
