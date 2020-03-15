package org.tasky.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tasky.backend.entity.enums.RoleType;
import org.tasky.backend.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleType name);
}
