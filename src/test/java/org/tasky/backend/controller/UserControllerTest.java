package org.tasky.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.tasky.backend.TestUtils;
import org.tasky.backend.config.TaskyConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/import-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/import-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {

    private UserController userController;
    private AuthController authController;
    private MockMvc mockMvc;
    private ObjectMapper MAPPER;
    private String AUTHORIZATION_HEADER = "Authorization";


    @Autowired
    public UserControllerTest(UserController userController,
                              AuthController authController,
                              ObjectMapper mapper,
                              MockMvc mockMvc) {
        this.authController = authController;
        this.userController = userController;
        this.MAPPER = mapper;
        this.mockMvc = mockMvc;
    }

    @Test
    public void shouldReturnUserByUsername() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String usernameToBeFound = "testAccount";
        final String userPath = TaskyConstants.USERS_PATH + "/" + usernameToBeFound;

        ResultActions result =
                mockMvc.perform(get(userPath)
                        .header(AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String returnedUsername = jsonParser.parseMap(resultString).get("username").toString();

        assertEquals(usernameToBeFound, returnedUsername, "Username should be equal");
    }


    @Test
    public void whenUserNotExists_thenReturnNotFoundMessage() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String userPath = TaskyConstants.USERS_PATH + "/notexisteduser";

        mockMvc.perform(get(userPath)
                .header(AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldReturnUserProject() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String userPathProject = TaskyConstants.USERS_PATH + "/testAccount/projects";

        mockMvc.perform(get(userPathProject)
                .header(AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken))
                .andExpect(status().isOk());
    }


    @Test
    public void whenUserWithGivenUsernameDoesNotExists_thenStatusIsNotFound() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String userPathProject = TaskyConstants.USERS_PATH + "/notexisted/projects";

        mockMvc.perform(get(userPathProject)
                .header(AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken))
                .andExpect(status().isNotFound());
    }


}