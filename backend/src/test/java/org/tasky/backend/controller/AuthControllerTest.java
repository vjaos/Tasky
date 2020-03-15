package org.tasky.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.tasky.backend.dto.request.LoginRequest;
import org.tasky.backend.entity.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@Sql(value = {"/import-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/import-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper MAPPER;


    @Test
    public void whenUserCreated_thenReturnStatusOK() throws Exception {
        User user = new User();
        user.setUsername("Test");
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("test123");

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }


    @Test
    public void whenRequestIsNotValid_thenStatusBadRequest() throws Exception {
        mockMvc.perform(post("/api/auth/signup"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUserAlreadyExists_thenStatusBadRequest() throws Exception {
        User user = new User();
        user.setUsername("jaje");
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("test123");

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAuthorizeUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jaje");
        loginRequest.setPassword("test123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }


    @Test
    public void whenCredentialIsWrong_thenStatusUnauthorized() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jaje");
        loginRequest.setPassword("t1231233");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

}
