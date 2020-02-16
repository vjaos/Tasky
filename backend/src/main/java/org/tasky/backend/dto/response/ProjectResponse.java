package org.tasky.backend.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.tasky.backend.entity.Project;

@Getter
@Setter
public class ProjectResponse {

    private String projectName;
    private String projectDescription;
    private String owner;

    public ProjectResponse(String projectName, String projectDescription, String owner) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.owner = owner;
    }

    public ProjectResponse(Project project) {
        this.projectName = project.getName();
        this.projectDescription = project.getDescription();
        this.owner = project.getUser().getUsername();
    }
}
