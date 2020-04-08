package org.tasky.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import org.tasky.backend.entity.Issue;
import org.tasky.backend.entity.Project;
import org.tasky.backend.entity.User;
import org.tasky.backend.entity.enums.IssueStatus;
import org.tasky.backend.repository.IssueRepository;
import org.tasky.backend.repository.ProjectRepository;
import org.tasky.backend.service.UserService;

import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/import-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/import-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProjectControllerTest {

    private ProjectController projectController;
    private final ObjectMapper MAPPER;
    private MockMvc mockMvc;
    private ProjectRepository projectRepository;
    private UserService userService;
    private IssueRepository issueRepository;
    private User user;
    private Project project;
    private Issue issue;
    private JacksonJsonParser jsonParser = new JacksonJsonParser();
    private Random random = new Random();


    @Autowired
    public ProjectControllerTest(ProjectController projectController,
                                 ObjectMapper MAPPER,
                                 MockMvc mockMvc,
                                 ProjectRepository projectRepository,
                                 UserService userService,
                                 IssueRepository issueRepository) {
        this.projectController = projectController;
        this.MAPPER = MAPPER;
        this.mockMvc = mockMvc;
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.issueRepository = issueRepository;
    }

    @BeforeEach
    public void initTestData() {
        this.user = userService.findUserByUsername("testAccount");

        Project project = new Project();
        project.setName("Tasky");
        project.setDescription("Task manager");
        project.setOwner(user);
        this.project = projectRepository.save(project);


        Issue issue = new Issue();

        issue.setTitle("Add new Interface");
        issue.setDescription("Some description");

        issue.setAuthor(this.user);
        issue.setProject(this.project);

        this.issue = issueRepository.save(issue);
    }

    @Test
    public void shouldReturnAllProjectIssues() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectIssuesPath = TaskyConstants.PROJECTS_PATH + "/" + project.getId() + "/issues";

        mockMvc.perform(get(projectIssuesPath)
                .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateNewIssue() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectIssuesPath = TaskyConstants.PROJECTS_PATH + "/" + project.getId() + "/issues";

        Issue issueData = new Issue();
        issueData.setTitle("New Title");
        issueData.setDescription("New Description");
        issueData.setIssueStatus(IssueStatus.NEW);

        ResultActions result =
                mockMvc.perform(post(projectIssuesPath)
                        .header(TaskyConstants.AUTHORIZATION_HEADER,
                                TaskyConstants.TOKEN_TYPE + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(issueData)))
                        .andExpect(status().isCreated());

        String resultString = result.andReturn().getResponse().getContentAsString();
        String returnedIssueTitle = jsonParser.parseMap(resultString).get("title").toString();
        String returnedDescription = jsonParser.parseMap(resultString).get("description").toString();

        assertEquals(issueData.getTitle(), returnedIssueTitle, "Issue title should be equal");
        assertEquals(issueData.getDescription(), returnedDescription, "Issue description should be equal");
    }

    @Test
    public void whenIssuesPostRequestReceiveInvalidData_thenStatusIsBadRequest() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectIssuesPath = TaskyConstants.PROJECTS_PATH + "/" + project.getId() + "/issues";

        Issue issueData = new Issue();

        mockMvc.perform(post(projectIssuesPath)
                .header(TaskyConstants.AUTHORIZATION_HEADER,
                        TaskyConstants.TOKEN_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(issueData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnProjectByItsId() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectPath = TaskyConstants.PROJECTS_PATH + "/" + project.getId();

        ResultActions result =
                mockMvc.perform(get(projectPath)
                        .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.name", is(project.getName())))
                        .andExpect(jsonPath("$.description", is(project.getDescription())));
    }

    @Test
    public void whenProjectIdIsWrong_thenStatusNotFound() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectPath = TaskyConstants.PROJECTS_PATH + "/" + 16L;

        mockMvc.perform(get(projectPath)
                .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateProject() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectPath = TaskyConstants.PROJECTS_PATH + "/" + project.getId();

        Project projectData = new Project();
        projectData.setName("New Project");
        projectData.setDescription("Description");

        mockMvc.perform(put(projectPath)
                .header(TaskyConstants.AUTHORIZATION_HEADER,
                        TaskyConstants.TOKEN_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(projectData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(projectData.getName())))
                .andExpect(jsonPath("$.description", is(projectData.getDescription())));

    }


    @Test
    public void whenProjectPutRequestReceiveWrongId_thenStatusNotFound() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectPath = TaskyConstants.PROJECTS_PATH + "/" + new Random().nextLong();

        Project projectData = new Project();
        projectData.setName("New Project");
        projectData.setDescription("Description");

        mockMvc.perform(put(projectPath)
                .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(projectData)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateNewProject() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectPath = TaskyConstants.PROJECTS_PATH + "/";

        Project projectData = new Project();
        projectData.setName("New Project");
        projectData.setDescription("Description");

        ResultActions result =
                mockMvc.perform(post(projectPath)
                        .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(projectData)))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();
        String returnedProjectName = jsonParser.parseMap(resultString).get("name").toString();
        String returnedDescription = jsonParser.parseMap(resultString).get("description").toString();

        assertEquals(projectData.getName(), returnedProjectName, "Project name should be equals");
        assertEquals(projectData.getDescription(), returnedDescription, "Project description should be equal");
    }

    @Test
    public void whenRequestReceiveInvalidDataForCreatingProject_thenStatusIsBadRequest() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectPath = TaskyConstants.PROJECTS_PATH + "/";

        Project project = new Project();

        mockMvc.perform(post(projectPath)
                .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(project)))
                .andExpect(status().isBadRequest());
    }
}