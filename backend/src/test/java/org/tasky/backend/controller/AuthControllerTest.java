package org.tasky.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.tasky.backend.dto.request.SignUpRequest;
import org.tasky.backend.security.jwt.AuthEntryPointJwt;
import org.tasky.backend.service.UserService;
import org.tasky.backend.service.security.PostgresUserDetailService;
import org.tasky.backend.utils.JwtUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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


    private SignUpRequest request;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @BeforeEach
    public void setup() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("Test");
        request.setEmail("test@test.com");
        request.setFirstName("Test");
        request.setLastName("Test");
        request.setPassword("test123");
        this.request = request;
    }

    @Test
    public void whenUserCreated_thenReturnStatusOK() throws Exception {
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }


    @Test
    public void whenRequestIsNotValid_thenStatusBadRequest() throws Exception {
        mockMvc.perform(post("/auth/signup"))
                .andExpect(status().isBadRequest());
    }

}
