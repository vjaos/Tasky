package org.tasky.backend.service;

import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;

import java.util.Optional;

public interface ProjectService {

    Project save(Project project);

    Optional<Project> getProjectByUserAndProjectId(User user, Long id);
}
