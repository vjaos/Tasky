package org.tasky.backend.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private String username;



    public JwtResponse(String token, String username) {
        this.accessToken = token;
        this.username = username;
    }

}
