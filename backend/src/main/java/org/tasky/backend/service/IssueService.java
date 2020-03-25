package org.tasky.backend.service;

import org.tasky.backend.entity.Issue;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    List<Issue> getIssuesByProjectId(Long projectId);

    void createIssue(Issue issueData, Long projectId, String username);
}
