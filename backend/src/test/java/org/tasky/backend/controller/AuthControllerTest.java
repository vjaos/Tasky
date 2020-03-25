package org.tasky.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.tasky.backend.config.TaskyConstants;
import org.tasky.backend.dto.request.LoginRequest;
import org.tasky.backend.entity.User;
import org.tasky.backend.entity.enums.Status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {
    private AuthController authController;
    private MockMvc mockMvc;
    private ObjectMapper MAPPER;
    private final String SIGN_UP_PATH = TaskyConstants.AUTH_PATH + "/signup";
    private final String LOGIN_PATH = TaskyConstants.AUTH_PATH + "/login";

    @Autowired
    public AuthControllerTest(AuthController authController,
                              MockMvc mockMvc,
                              ObjectMapper MAPPER) {
        this.authController = authController;
        this.mockMvc = mockMvc;
        this.MAPPER = MAPPER;
    }


    @Test
    public void whenUserCreated_thenReturnStatusCreated() throws Exception {
        User user = new User();
        user.setUsername("Test");
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("test123");


        mockMvc.perform(post(SIGN_UP_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }


    @Test
    public void whenRequestIsNotValid_thenStatusBadRequest() throws Exception {
        mockMvc.perform(post(SIGN_UP_PATH))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(value = {"/import-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/import-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void whenUserAlreadyExists_thenStatusBadRequest() throws Exception {
        User user = new User();
        user.setUsername("jaje");
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("test123");
        user.setStatus(Status.ACTIVE);

        mockMvc.perform(post(SIGN_UP_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(value = {"/import-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/import-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldAuthorizeUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jaje");
        loginRequest.setPassword("test123");

        mockMvc.perform(post(LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }


    @Test
    public void whenCredentialIsWrong_thenStatusUnauthorized() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jaje");
        loginRequest.setPassword("t1231233");

        mockMvc.perform(post(LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

}
