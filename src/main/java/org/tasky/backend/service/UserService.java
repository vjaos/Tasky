package org.tasky.backend.service;

import org.tasky.backend.dto.request.LoginRequest;
import org.tasky.backend.entity.User;

public interface UserService {

    String authenticate(LoginRequest loginRequest);

    void saveUser(User user);

    void deleteUserById(Long id);

    User findUserByUsername(String username);

}
