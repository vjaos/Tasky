package org.tasky.backend.config;

public interface TaskyConstants {
    String API_URL = "/api/";
    String AUTH = "auth";
    String TASKS = "tasks";
    String PROJECTS = "projects";
    String USERS = "users";


    String AUTH_PATH = API_URL + AUTH;
    String PROJECTS_PATH = API_URL + PROJECTS;
    String ISSUES_PATH = API_URL + TASKS;
    String USERS_PATH = API_URL + USERS;

}
