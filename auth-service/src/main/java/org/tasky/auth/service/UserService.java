package org.tasky.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tasky.auth.entity.User;
import org.tasky.auth.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final BCryptPasswordEncoder bcryptEncode = new BCryptPasswordEncoder();


    public void create(User user) {
        Optional<User> existingUsr = userRepository.findById(user.getUsername());
        existingUsr.ifPresent(usr -> {
            throw new IllegalArgumentException("User already exist: " + user.getUsername());
        });
        String pwdHash = bcryptEncode.encode(user.getPassword());
        user.setPassword(pwdHash);
        userRepository.save(user);
    }
}
