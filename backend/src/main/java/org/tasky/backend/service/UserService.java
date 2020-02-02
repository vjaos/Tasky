package org.tasky.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tasky.backend.entity.ERole;
import org.tasky.backend.entity.User;
import org.tasky.backend.payload.request.SignUpRequest;
import org.tasky.backend.repository.RoleRepository;
import org.tasky.backend.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passEncoder;
    @Autowired
    private RoleRepository roleRepository;


    @Transactional
    public void createUser(@NonNull SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passEncoder.encode(signUpRequest.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByName(ERole.ROLE_USER)));
        userRepository.save(user);
    }

    public boolean isUsernameTaken(@NonNull String username){
        return userRepository.existsUserByUsername(username);
    }

}
