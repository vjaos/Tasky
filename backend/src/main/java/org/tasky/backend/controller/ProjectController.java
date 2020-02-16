package org.tasky.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.tasky.backend.dto.response.ProjectResponse;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.dto.request.ProjectCreationRequest;
import org.tasky.backend.dto.response.MessageResponse;
import org.tasky.backend.service.ProjectService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(@AuthenticationPrincipal User user,
                                           @Valid @RequestBody ProjectCreationRequest project) {
        if (projectService.isProjectExist(project.getProjectName())) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Project " + project.getProjectName() + " already exist"));

        } else {
            projectService.createProject(project, user);
            return new ResponseEntity<>(new MessageResponse("Project successfully created"), HttpStatus.CREATED);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getProject(@PathVariable("name") String name) {
        Optional<Project> project = projectService.findProject(name);
        return project.isPresent() ?
                ResponseEntity.ok(new ProjectResponse(project.get())) :
                ResponseEntity.badRequest().body(new MessageResponse("Cannot find project with name: " + name));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteProject(@PathVariable("name") Project project) {
        projectService.deleteProject(project);
        return ResponseEntity.ok(new MessageResponse("Project was successfully deleted!"));
    }

    @PutMapping
    public void updateProject(ProjectCreationRequest request) {

    }


}
