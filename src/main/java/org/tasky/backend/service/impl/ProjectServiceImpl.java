package org.tasky.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.repository.UserRepository;
import org.tasky.backend.service.ProjectService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {


    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    public static final String PROJECT_NOT_FOUND = "Project with id '%d' not found";

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
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
    @Transactional
    public Project createProject(Project projectData, String username) throws EntityNotFoundException {

        return userRepository.findByUsername(username)
                .map(user -> {
                    Project project = new Project();
                    project.setName(projectData.getName());
                    project.setDescription(projectData.getDescription());
                    project.setOwner(user);
                    return projectRepository.save(project);
                }).orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format(UserServiceImpl.USER_NOT_FOUND, username)));
    }

    /**
     * Update project according to given data or create it
     *
     * @param projectData for project that will be updated
     * @param projectId   of project to be updated
     * @return Optional<Project> updated project
     */
    @Override
    @Transactional
    public Project updateProject(Project projectData, Long projectId) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    project.setName(projectData.getName());
                    project.setDescription(projectData.getDescription());
                    return projectRepository.save(project);
                })
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format(PROJECT_NOT_FOUND, projectId)));
    }

    /**
     * Searches for a project according to given Id
     *
     * @param id of project to be found
     * @return found project
     * @throws EntityNotFoundException if there isn't such project
     */
    @Override
    @Transactional(readOnly = true)
    public Project findProjectById(Long id) throws EntityNotFoundException {
        return projectRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format(PROJECT_NOT_FOUND, id)));
    }

    /**
     * Searches for projects of a given user
     *
     * @param username of user whose projects you want to get
     * @return List of projects created by given user
     * @throws EntityNotFoundException if user with give username doesn't exists
     */
    @Override
    @Transactional(readOnly = true)
    public List<Project> getAllUserProjects(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username)
                .map(User::getProjects)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format(UserServiceImpl.USER_NOT_FOUND, username)));
    }

}
