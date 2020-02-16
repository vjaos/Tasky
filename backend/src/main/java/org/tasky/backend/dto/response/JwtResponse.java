package org.tasky.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    private String username;
    private List<String> roles;


    public JwtResponse(String token, String username, List<String> roles){
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

}
