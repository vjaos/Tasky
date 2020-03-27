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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    public void whenGivenIncorrectProjectId_thenStatusIsNotFound() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectIssuesPath = TaskyConstants.PROJECTS_PATH + "/"
                + (project.getId() + random.nextLong())
                + "/issues";

        mockMvc.perform(get(projectIssuesPath)
                .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken))
                .andExpect(status().isNotFound());

    }


    @Test
    public void whenGivenWrongIssueId_thenStatusIsNotFound() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectIssuePath = TaskyConstants.PROJECTS_PATH + "/" + project.getId()
                + "/issues/" + issue.getId() + random.nextLong();


        mockMvc.perform(get(projectIssuePath)
                .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldUpdateIssue() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectIssuePath = TaskyConstants.PROJECTS_PATH + "/" + project.getId()
                + "/issues/" + issue.getId();

        Issue issueData = new Issue();
        issueData.setTitle("Updated Title");
        issueData.setDescription("Updated Description");
        issueData.setIssueStatus(IssueStatus.NEW);

        ResultActions result = mockMvc.perform(put(projectIssuePath)
                .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(issueData)))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        String returnedIssueTitle = jsonParser.parseMap(resultString).get("title").toString();
        Long returnedId = Long.parseLong(jsonParser.parseMap(resultString).get("id").toString());
        String returnedDescription = jsonParser.parseMap(resultString).get("description").toString();

        assertEquals(issue.getId(), returnedId, "Issue id shouldn't change");
        assertEquals(issueData.getTitle(), returnedIssueTitle, "Issue title should be equal");
        assertEquals(issueData.getDescription(), returnedDescription, "Issue description should be equal");
    }

    @Test
    public void whenGiveDataIsInvalid_thenStatusIsBadRequest() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectIssuePath = TaskyConstants.PROJECTS_PATH + "/" + project.getId()
                + "/issues/" + issue.getId();

        Issue issueData = new Issue();

        mockMvc.perform(put(projectIssuePath)
                .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(issueData)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void whenPutRequestReceiveIncorrectIssueId_thenStatusIsNotFound() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectIssuePath = TaskyConstants.PROJECTS_PATH + "/" + project.getId()
                + "/issues/" + Long.valueOf(150L).toString();

        Issue issueData = new Issue();
        issueData.setTitle("Updated Title");
        issueData.setDescription("Updated Description");
        issueData.setIssueStatus(IssueStatus.NEW);

        mockMvc.perform(put(projectIssuePath)
                .header(TaskyConstants.AUTHORIZATION_HEADER, TaskyConstants.TOKEN_TYPE + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(issueData)))
                .andExpect(status().isNotFound());
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
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();
        String returnedProjectName = jsonParser.parseMap(resultString).get("name").toString();
        String returnedProjectDescription = jsonParser.parseMap(resultString).get("description").toString();

        assertEquals(project.getName(), returnedProjectName, "Name should be equals");
        assertEquals(project.getDescription(), returnedProjectDescription, "Description should be equals");
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
    public void shouldUpdateNewProject() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectPath = TaskyConstants.PROJECTS_PATH + "/" + project.getId();

        Project projectData = new Project();
        projectData.setName("New Project");
        projectData.setDescription("Description");


        ResultActions result =
                mockMvc.perform(put(projectPath)
                        .header(TaskyConstants.AUTHORIZATION_HEADER,
                                TaskyConstants.TOKEN_TYPE + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(projectData)))
                        .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        String returnedProjectName = jsonParser.parseMap(resultString).get("name").toString();
        String returnedDescription = jsonParser.parseMap(resultString).get("description").toString();

        assertEquals(projectData.getName(), returnedProjectName, "Project name should be equals");
        assertEquals(projectData.getDescription(), returnedDescription, "Project description should be equal");
    }


    @Test
    public void whenProjectPutRequestReceiveWrongId_thenStatusNotFound() throws Exception {
        final String accessToken = TestUtils.obtainAccessToken(mockMvc, MAPPER);
        final String projectPath = TaskyConstants.PROJECTS_PATH + "/" + project.getId() + 16;

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