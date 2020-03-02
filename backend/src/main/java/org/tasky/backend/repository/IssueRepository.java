package org.tasky.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    boolean existsByTitleAndProject(String title, Project project);
}
