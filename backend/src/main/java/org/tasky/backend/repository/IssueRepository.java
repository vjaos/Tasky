package org.tasky.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findAllByProject(Project project);

    Optional<Issue> findByIdAndProject(Long id, Project project);

}
