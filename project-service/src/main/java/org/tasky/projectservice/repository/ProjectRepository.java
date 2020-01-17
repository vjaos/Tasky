package org.tasky.projectservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tasky.projectservice.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
