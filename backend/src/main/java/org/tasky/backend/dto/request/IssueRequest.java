package org.tasky.backend.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * DTO class used for creating issues for project.
 *
 * @see org.tasky.backend.entity.Issue
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class IssueRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
