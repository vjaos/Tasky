package org.tasky.backend.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tasky.backend.config.TaskyConstants;

@RestController
@RequestMapping(value = TaskyConstants.PROJECTS_PATH)
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {
}
