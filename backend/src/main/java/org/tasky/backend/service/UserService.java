package org.tasky.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tasky.backend.entity.User;
import org.tasky.backend.dto.request.SignUpRequest;
import org.tasky.backend.repository.RoleRepository;
import org.tasky.backend.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.tasky.backend.entity.enums.ERole.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passEncoder;
    @Autowired
    private RoleRepository roleRepository;


    public void createUser(@NonNull SignUpRequest signUpRequest) {
        User user = new User();

        user.setUsername(signUpRequest.getUsername());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passEncoder.encode(signUpRequest.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByName(ROLE_USER)));

        userRepository.save(user);
    }

    public boolean isUsernameTaken(@NonNull String username) {
        return userRepository.existsUserByUsername(username);
    }
}
