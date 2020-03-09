package org.tasky.backend.dto.response;

import org.tasky.backend.entity.Issue;


public class IssuesResponse {

    private String title;
    private String descrtiption;


    public IssuesResponse(Issue issue) {
        this.title = issue.getTitle();
        this.descrtiption = issue.getDescription();
    }


}
