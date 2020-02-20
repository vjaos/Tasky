package org.tasky.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tasky.backend.repository.UserRepository;


/**
 * {@link UserDetailsService} implementation
 *
 * @author Vyacheslav Osipov
 */
@Service
public class PostgresUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method which load User by username and returns a {@link UserDetails} object
     * that Spring Security can use for authentication and validation
     *
     * @param username of User that should be authenticated
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException, if there is no such user
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User " + username + " not found"));
    }
}
