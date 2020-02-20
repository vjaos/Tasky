package org.tasky.backend.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.tasky.backend.entity.User;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class which main function is:
 * <ul>
 *     <li>generate a JWT</li>
 *     <li>extract username from JWT</li>
 *     <li>validate JWT</li>
 * </ul>
 * @see Jwts
 */
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final int JWT_EXPIRATION_DAYS = 14;

    @Value("${TASKY_JWT_SECRET:taskySecret}")
    private String jwtSecret;


    public String generateJwtToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, JWT_EXPIRATION_DAYS); //Set token expiration time for 2 week

        return Jwts.builder().
                setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}
