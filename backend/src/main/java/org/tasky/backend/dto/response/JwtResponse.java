package org.tasky.backend.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";

    private String username;
    private List<String> roles;


    public JwtResponse(String token, String username, List<String> roles) {
        this.accessToken = token;
        this.username = username;
        this.roles = roles;
    }

}
