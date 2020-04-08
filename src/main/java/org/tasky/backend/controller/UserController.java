package org.tasky.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tasky.backend.config.TaskyConstants;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.service.ProjectService;
import org.tasky.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = TaskyConstants.USERS_PATH)
public class UserController {
    private UserService userService;
    private ProjectService projectService;

    @Autowired
    public UserController(UserService userService,
                          ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserProjects(@PathVariable("username") String username) {
        List<Project> projects = projectService.getAllUserProjects(username);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
