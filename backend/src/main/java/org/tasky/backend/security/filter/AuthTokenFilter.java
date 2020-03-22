package org.tasky.backend.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tasky.backend.entity.User;
import org.tasky.backend.service.security.PostgresUserDetailService;
import org.tasky.backend.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Custom authorization filter
 * which makes a single execution for each request to API
 *
 * @author Vyacheslav Osipov
 * @see OncePerRequestFilter
 * @see JwtUtils
 * @see PostgresUserDetailService
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTH_HEADER = "Authorization";
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PostgresUserDetailService userDetailService;


    /**
     * Method implement parsing and validating of JWT,
     * loading User details using {@link PostgresUserDetailService},
     * checking Authorization using {@link UsernamePasswordAuthenticationToken}
     *
     * @param req         {@link HttpServletRequest}
     * @param resp        {@link HttpServletResponse}
     * @param filterChain {@link FilterChain}
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(req);

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUsernameFromJwtToken(jwt);

                User userDetails = (User) userDetailService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: " + e.getMessage());
        }

        filterChain.doFilter(req, resp);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTH_HEADER);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_PREFIX)) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
