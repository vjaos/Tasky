package org.tasky.backend.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.tasky.backend.config.TaskyConstants;
import org.tasky.backend.entity.Role;
import org.tasky.backend.exception.JwtAuthenticationException;
import org.tasky.backend.service.security.PostgresUserDetailService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private final static int JWT_EXPIRATION_DAYS = 14;
    @Value("${jwt.token.secret:Secret}")
    private String secret;

    @Autowired
    private PostgresUserDetailService userDetailsService;


    public String createToken(String username) {

        Claims claims = Jwts.claims().setSubject(username);

        Calendar validity = Calendar.getInstance();
        validity.setTime(new Date());
        validity.add(Calendar.DAY_OF_YEAR, JWT_EXPIRATION_DAYS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(validity.getTime())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith(TaskyConstants.TOKEN_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }


}
