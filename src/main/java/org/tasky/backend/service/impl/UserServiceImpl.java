package org.tasky.backend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.entity.enums.RoleType;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.repository.RoleRepository;
import org.tasky.backend.repository.UserRepository;
import org.tasky.backend.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passEncoder;
    private RoleRepository roleRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passEncoder,
                           RoleRepository roleRepository,
                           ProjectRepository projectRepository) {

        this.userRepository = userRepository;
        this.passEncoder = passEncoder;
        this.roleRepository = roleRepository;
        this.projectRepository = projectRepository;
    }

    public void saveUser(@NonNull User user) throws IllegalArgumentException {
        Optional<User> existing = userRepository.findByUsername(user.getUsername());

        existing.ifPresent(it -> {
            throw new IllegalArgumentException("User already exists: " + it.getUsername());
        });

        user.setPassword(passEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepository.findByName(RoleType.ROLE_USER.toString()));

        userRepository.save(user);
    }


    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<Project> getUserProjectById(String username, Long projectId) {
        User user = findUserByUsername(username);

        return projectRepository.findProjectByOwnerAndId(user, projectId);
    }


    @Override
    public User findUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("User with username %s not found", username))
                );
    }

}
