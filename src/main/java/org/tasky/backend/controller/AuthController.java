package org.tasky.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.tasky.backend.config.TaskyConstants;
import org.tasky.backend.dto.request.LoginRequest;
import org.tasky.backend.dto.response.JwtResponse;
import org.tasky.backend.entity.User;
import org.tasky.backend.security.jwt.JwtTokenProvider;
import org.tasky.backend.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = TaskyConstants.AUTH_PATH)
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        final String username = loginRequest.getUsername();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword())
        );

        String jwt = jwtTokenProvider.createToken(username);

        return ResponseEntity.ok(new JwtResponse(jwt, username));
    }


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
        } catch (IllegalArgumentException iae) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, iae.getMessage(), iae);
        }
    }
}


