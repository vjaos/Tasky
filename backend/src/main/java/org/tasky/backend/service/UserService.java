package org.tasky.backend.service;

import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;

import java.util.Optional;

public interface UserService {

    void saveUser(User user);

    void deleteUserById(Long id);

    Optional<Project> getUserProjectById(String username, Long projectId);

    User findUserByUsername(String username);

}
