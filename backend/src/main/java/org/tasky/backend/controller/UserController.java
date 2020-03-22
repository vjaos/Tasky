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
import org.tasky.backend.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping(value = TaskyConstants.USERS_PATH)
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{username}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable("username") User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}/projects/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserProjects(@PathVariable("username") User user) {
        return new ResponseEntity<>(user.getProjects(), HttpStatus.OK);
    }

    @GetMapping(value = "/{username}/projects/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getParticularProject(@PathVariable Long projectId,
                                                  @PathVariable("username") User user) {
        Optional<Project> project = userService.getProjectByUserAndProjectId(user, projectId);
        if (project.isPresent()) {
            return new ResponseEntity<>(project.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
