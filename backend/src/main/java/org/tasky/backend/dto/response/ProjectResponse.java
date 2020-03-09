package org.tasky.backend.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.tasky.backend.entity.Project;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProjectResponse {

    private String projectName;
    private String projectDescription;
    private String owner;

    public ProjectResponse(Project project) {
        this.projectName = project.getName();
        this.projectDescription = project.getDescription();
        this.owner = project.getUser().getUsername();
    }
}
