package org.tasky.backend.service;

import org.tasky.backend.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Optional<Project> createProject(Project project, String username);

    Optional<Project> updateProject(Project project, Long projectId);

    Project findProjectById(Long id);

    List<Project> getAllUserProject(String username);
}
