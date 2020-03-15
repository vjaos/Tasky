package org.tasky.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findProjectByOwnerAndId(User owner, Long id);

    Optional<Project> findProjectByName(String name);
}
