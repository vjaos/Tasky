package org.tasky.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.tasky.backend.dto.request.IssueRequest;
import org.tasky.backend.dto.request.ProjectCreationRequest;
import org.tasky.backend.dto.response.IssuesResponse;
import org.tasky.backend.dto.response.MessageResponse;
import org.tasky.backend.dto.response.ProjectResponse;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.service.IssueService;
import org.tasky.backend.service.ProjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private IssueService issueService;

    @GetMapping("/{name}")
    public ResponseEntity<?> getProject(@PathVariable("name") Project project) {
        return ResponseEntity.ok(new ProjectResponse(project));
    }

    @PostMapping
    public ResponseEntity<?> createProject(@AuthenticationPrincipal User user,
                                           @Valid @RequestBody ProjectCreationRequest project) {

        if (projectService.isProjectExist(project.getProjectName())) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Project " + project.getProjectName() + " already exist"));

        } else {
            projectService.save(project, user);
            return new ResponseEntity<>(new MessageResponse("Project successfully created"), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteProject(@PathVariable("name") Project project) {
        projectService.deleteProject(project);
        return ResponseEntity.ok(new MessageResponse("Project was successfully deleted!"));
    }


    @GetMapping("/{name}/issues")
    public List<IssuesResponse> getAllIssues(@PathVariable("name") Project project) {
        List<Issue> issues = project.getIssues();
        return issues.stream().map(IssuesResponse::new).collect(Collectors.toList());
    }


    @PostMapping("/{name}/issues")
    public ResponseEntity<?> createProjectIssue(@PathVariable("name") Project project,
                                                @Valid @RequestBody IssueRequest issue) {
        if (projectService.containsIssue(issue.getTitle(), project)) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Project already contains this issue: " + issue.getTitle()));
        } else {
            issueService.createIssue(project, issue);
            return new ResponseEntity<>(new MessageResponse("Issue created!"), HttpStatus.CREATED);
        }
    }


}
