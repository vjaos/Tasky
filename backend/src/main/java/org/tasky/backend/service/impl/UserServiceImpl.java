package org.tasky.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.entity.enums.RoleType;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.repository.RoleRepository;
import org.tasky.backend.repository.UserRepository;
import org.tasky.backend.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProjectRepository projectRepository;


    public User saveUser(@NonNull User user) {
        Optional<User> existing = userRepository.findByUsername(user.getUsername());
        existing.ifPresent(it -> {
            throw new IllegalArgumentException("User already exists: " + it.getUsername());
        });

        user.setPassword(passEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepository.findByName(RoleType.ROLE_USER.toString()));

        return userRepository.save(user);
    }


    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<Project> getProjectByUserAndProjectId(User user, Long projectId) {
        return projectRepository.findProjectByOwnerAndId(user, projectId);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
