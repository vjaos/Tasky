package org.tasky.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findAllByProject(Project project);

    List<Issue> findByProjectAndId(Project project, Long id);

    void deleteAllByProject(Project project);
}
