package org.tasky.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.tasky.backend.dto.request.SignUpRequest;
import org.tasky.backend.security.jwt.AuthEntryPointJwt;
import org.tasky.backend.service.security.PostgresUserDetailService;
import org.tasky.backend.service.UserService;
import org.tasky.backend.utils.JwtUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private PostgresUserDetailService userDetailService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void whenUserCreated_thenReturnMessage() throws Exception {
        SignUpRequest request = new SignUpRequest();

        request.setUsername("TestUser");
        request.setEmail("test@test.com");
        request.setFirstName("Test");
        request.setLastName("Test");
        request.setPassword("test123");

        mockMvc.perform(post("/auth/signup")
                .content(MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("User registered successfully!")));
    }


}
