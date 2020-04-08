package org.tasky.backend.service;

import org.tasky.backend.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Project createProject(Project project, String username);

    Project updateProject(Project project, Long projectId);

    Project findProjectById(Long id);

    List<Project> getAllUserProjects(String username);
}
