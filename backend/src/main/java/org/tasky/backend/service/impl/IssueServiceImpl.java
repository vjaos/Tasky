package org.tasky.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.repository.IssueRepository;

@Service
public class IssueServiceImpl {

    @Autowired
    private IssueRepository issueRepository;


    public Issue save(Issue issue) {
        return issueRepository.save(issue);
    }

}
