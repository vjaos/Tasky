package org.tasky.backend.service;

import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    void deleteUserById(Long id);

    Optional<Project> getProjectByUserAndProjectId(User user, Long projectId);

    Optional<User> findUserByUsername(String username);

}
