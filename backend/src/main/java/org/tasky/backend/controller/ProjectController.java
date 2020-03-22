package org.tasky.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.tasky.backend.config.TaskyConstants;
import org.tasky.backend.entity.Project;
import org.tasky.backend.security.jwt.JwtUserDetails;
import org.tasky.backend.service.ProjectService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = TaskyConstants.PROJECTS_PATH)
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @PostMapping(name = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProject(@AuthenticationPrincipal JwtUserDetails jwtUserDetails,
                                           @Valid @RequestBody Project project) {

        Optional<Project> savedProject = projectService.save(project, jwtUserDetails.getUsername());

        return savedProject.isPresent() ?
                new ResponseEntity<>(savedProject.get(), HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(name = "/{projectId}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProject(@PathVariable("projectId") Long projectId,
                                           @Valid @RequestBody Project projectData) {
        Optional<Project> updatedProject = projectService.updateProject(projectData, projectId);

        return updatedProject.isPresent() ?
                new ResponseEntity<>(updatedProject.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
