package org.tasky.backend.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tasky.backend.entity.User;
import org.tasky.backend.repository.UserRepository;
import org.tasky.backend.security.jwt.JwtUserDetailsFactory;
import org.tasky.backend.service.impl.UserServiceImpl;


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
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format(UserServiceImpl.USER_NOT_FOUND, username)));

        return JwtUserDetailsFactory.create(user);
    }
}
