package org.tasky.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.tasky.backend.dto.request.IssueRequest;
import org.tasky.backend.dto.request.ProjectRequest;
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
    public ProjectResponse getProject(@PathVariable("name") Project project) {
        return new ProjectResponse(project);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@AuthenticationPrincipal User user,
                              @Valid @RequestBody ProjectRequest project) {
        projectService.save(project, user);
    }


    @GetMapping("/{name}/issues")
    public List<IssuesResponse> getAllIssues(@PathVariable("name") Project project) {
        List<Issue> issues = project.getIssues();
        return issues.stream().map(IssuesResponse::new).collect(Collectors.toList());
    }


    @PostMapping("/{name}/issues")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProjectIssue(@PathVariable("name") Project project,
                                   @Valid @RequestBody IssueRequest issue) {
        issueService.createIssue(project, issue);
    }

}
