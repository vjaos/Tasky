package org.tasky.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tasky.backend.config.TaskyConstants;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.service.IssueService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = TaskyConstants.ISSUES_PATH)
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PutMapping(value = "/{issueId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateIssue(
            @PathVariable("issueId") Long issueId,
            @RequestBody @Valid Issue issueData) {
        Issue issue = issueService.updateIssue(issueId, issueData);

        return new ResponseEntity<>(issue, HttpStatus.OK);
    }


    @GetMapping(value = "/{issueId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getIssueByIdAndProjectId(@PathVariable("issueId") Long issueId) {

        return issueService.getOne(issueId)
                .map(it -> new ResponseEntity<>(it, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
