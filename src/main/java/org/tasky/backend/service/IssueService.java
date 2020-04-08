package org.tasky.backend.service;

import org.tasky.backend.entity.Issue;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    List<Issue> getIssuesByProjectId(Long projectId);

    Optional<Issue> getOne(Long issueId);

    Issue updateIssue(Long issueId, Issue issueData);

    Issue createIssue(Issue issueData, Long projectId, String username);
}
