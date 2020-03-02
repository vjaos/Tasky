package org.tasky.backend.dto.response;

import lombok.AllArgsConstructor;
import org.tasky.backend.entity.Issue;

@AllArgsConstructor
public class IssuesResponse {

    private String title;
    private String descrtiption;


    public IssuesResponse(Issue issue) {
        this.title = issue.getTitle();
        this.descrtiption = issue.getDescription();
    }


}
