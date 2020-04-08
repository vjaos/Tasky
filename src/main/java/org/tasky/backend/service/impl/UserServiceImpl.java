package org.tasky.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tasky.backend.entity.User;
import org.tasky.backend.entity.enums.RoleType;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.repository.RoleRepository;
import org.tasky.backend.repository.UserRepository;
import org.tasky.backend.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public static final String  USER_NOT_FOUND = "User %s not found";

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
    public User findUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(USER_NOT_FOUND, username))
                );
    }

}
