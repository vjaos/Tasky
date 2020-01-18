package org.tasky.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tasky.auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
