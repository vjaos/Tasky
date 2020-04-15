package org.tasky.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private UserService userService;


    @Autowired
    public AuthController(UserService userService ){
        this.userService = userService;

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        String jwt = userService.authenticate(loginRequest);

        return ResponseEntity.ok(new JwtResponse(jwt, loginRequest.getUsername()));
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


