package org.tasky.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tasky.backend.entity.IssueStatus;

@Repository
@Transactional(readOnly = true)
public interface IssueStatusRepository extends JpaRepository<IssueStatus, Long> {
}
