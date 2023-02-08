package com.rahulshettyacademy;

import com.rahulshettyacademy.rest.api.*;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Random;

public class BaseTest {
    protected String projectKey;
    protected String projectId;
    protected SessionFilter sessFilter;

    @BeforeClass
    public void setup() {
        sessFilter = new SessionFilter();
        //Login
        Authentication.login("olegati1", "OlekKuzmanow1997!", sessFilter);

        //Create Project
        int rndNum = new Random().nextInt(100);
        String name = "Example Project" + rndNum;
        String key = "EXA" + rndNum;

        String createProjectRequest = ProjectRestApi.createProject(sessFilter, name, key);
        JsonPath jspCreateProject = new JsonPath(createProjectRequest);

        projectKey = jspCreateProject.getString("key");
        projectId = jspCreateProject.getString("id");
    }

    @AfterClass
    public void tearDown() {
        ProjectRestApi.deleteProject(sessFilter, projectKey);
    }
}
