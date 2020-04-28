package org.tasky.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.tasky.backend.common.TaskyConstants;
import org.tasky.backend.dto.request.LoginRequest;
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.entity.enums.Status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUtils {


    public static User initUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test123");
        user.setEmail("test@test.com");
        user.setFirstName("Bla");
        user.setLastName("bla");
        user.setStatus(Status.ACTIVE);
        return user;
    }

    public static Project initProject(User user) {
        Project project = new Project();
        project.setName("Name");
        project.setDescription("Desc");
        project.setOwner(user);
        return project;
    }

    public static Issue initIssue(User user, Project project) {
        Issue issue = new Issue();
        issue.setTitle("Issue");
        issue.setDescription("Desc");
        issue.setAuthor(user);
        issue.setProject(project);
        return issue;
    }

    public static String obtainAccessToken(MockMvc mockMvc, ObjectMapper mapper) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testAccount");
        loginRequest.setPassword("test123");

        final String loginPath = TaskyConstants.AUTH_PATH + "/login";

        ResultActions result =
                mockMvc.perform(post(loginPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginRequest)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}
