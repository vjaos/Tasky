package org.tasky.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProjectCreationRequest {
    @NotBlank
    private String projectName;
    @NotBlank
    private String projectDescription;

}
