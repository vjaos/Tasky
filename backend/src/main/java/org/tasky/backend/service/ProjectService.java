package org.tasky.backend.service;

import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;

import java.util.Optional;

public interface ProjectService {

    Optional<Project> save(Project project, String username);

    Optional<Project> updateProject(Project project, Long projectId);

}
