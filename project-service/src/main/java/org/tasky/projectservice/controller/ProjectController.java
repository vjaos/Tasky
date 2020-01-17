package org.tasky.projectservice.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tasky.projectservice.entity.Project;
import org.tasky.projectservice.repository.ProjectRepository;
import org.tasky.projectservice.service.ProjectService;

@RestController
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("{id}")
    public Project getOne(@PathVariable("id") Project project) {
        return project;
    }

    @PostMapping
    public Project create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @PutMapping("{id}")
    public Project update(@PathVariable("id") Project projectDb, @RequestBody Project project) {
        BeanUtils.copyProperties(project, projectDb, "id");
        return projectDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Project project) {
        projectService.delete(project);
    }


}
