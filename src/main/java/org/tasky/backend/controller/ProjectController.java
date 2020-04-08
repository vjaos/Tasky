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

    @GetMapping(value = "/{projectId}/issues", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllProjectIssues(@PathVariable("projectId") Long projectId) {

        List<Issue> issues = issueService.getIssuesByProjectId(projectId);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }



    @PostMapping(value = "/{projectId}/issues",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createIssue(@PathVariable("projectId") Long projectId,
                                         @RequestBody @Valid Issue issueData,
                                         @AuthenticationPrincipal JwtUserDetails userDetails) {

        Issue issue = issueService.createIssue(issueData, projectId, userDetails.getUsername());
        return new ResponseEntity<>(issue, HttpStatus.CREATED);
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProject(@PathVariable("id") Long id) {
        return new ResponseEntity<>(projectService.findProjectById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProject(@PathVariable("id") Long projectId,
                                           @Valid @RequestBody Project projectData) {
        Project updatedProject = projectService.updateProject(projectData, projectId);

        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProject(@AuthenticationPrincipal JwtUserDetails jwtUserDetails,
                                           @RequestBody @Valid Project projectData) {

        Project project = projectService.createProject(projectData, jwtUserDetails.getUsername());

        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }


}
