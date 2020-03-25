package org.tasky.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.tasky.backend.config.TaskyConstants;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.security.jwt.JwtUserDetails;
import org.tasky.backend.service.IssueService;
import org.tasky.backend.service.ProjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = TaskyConstants.PROJECTS_PATH)
public class ProjectController {

    private ProjectService projectService;
    private IssueService issueService;

    @Autowired
    public ProjectController(ProjectService projectService,
                             IssueService issueService) {
        this.projectService = projectService;
        this.issueService = issueService;
    }

    @GetMapping(name = "/{projectId}/issues", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllProjectIssues(@PathVariable("projectId") Long projectId) {
        List<Issue> issues = issueService.getIssuesByProjectId(projectId);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @GetMapping(name = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(projectService.findProjectById(id), HttpStatus.OK);
    }

    @PutMapping(name = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProject(@PathVariable("id") Long projectId,
                                           @Valid @RequestBody Project projectData) {
        Optional<Project> updatedProject = projectService.updateProject(projectData, projectId);

        return updatedProject.
                map(it -> new ResponseEntity<>(it, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping(name = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProject(@AuthenticationPrincipal JwtUserDetails jwtUserDetails,
                                           @Valid @RequestBody Project project) {

        Optional<Project> savedProject = projectService.createProject(project, jwtUserDetails.getUsername());

        return savedProject
                .map(it -> new ResponseEntity<>(it, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


}
