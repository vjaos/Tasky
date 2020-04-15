package org.tasky.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tasky.backend.dto.request.LoginRequest;
import org.tasky.backend.entity.User;
import org.tasky.backend.entity.enums.RoleType;
import org.tasky.backend.repository.RoleRepository;
import org.tasky.backend.repository.UserRepository;
import org.tasky.backend.security.jwt.JwtTokenProvider;
import org.tasky.backend.service.UserService;

import javax.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {
    public static final String USER_ALREADY_EXISTS = "User %s already exists";
    public static final String USER_NOT_FOUND = "User %s not found";

    private UserRepository userRepository;
    private PasswordEncoder passEncoder;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passEncoder,
                           RoleRepository roleRepository,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passEncoder = passEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Authenticate user in system and return JWT token
     *
     * @param loginRequest that contains username and password
     * @return JWT token for current user
     * @throws AuthenticationException if login data is incorrect
     * @see JwtTokenProvider
     */
    @Override
    public String authenticate(LoginRequest loginRequest) throws AuthenticationException {
        final String username = loginRequest.getUsername();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword())
        );

        return jwtTokenProvider.createToken(username);
    }

    public void saveUser(@NonNull User user) throws IllegalArgumentException {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(it -> {
                    throw new IllegalArgumentException(
                            String.format(USER_ALREADY_EXISTS, it.getUsername())
                    );
                });

        user.setPassword(passEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepository.findByName(RoleType.ROLE_USER.toString()));

        userRepository.save(user);
    }


    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format(USER_NOT_FOUND, username)));
    }

}
