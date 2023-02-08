package com.rahulshettyacademy;

import com.rahulshettyacademy.rest.api.IssueRestApi;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class DeleteIssueTests extends BaseTest {

    @Test
    public void test_deleteIssue() {
        String summary = "Issue from RestAssured.";
        String description = "Description form RestAssured.";
        String issueType = "Task";

        String createIssueResponse = IssueRestApi.createIssue(sessFilter, projectKey, summary, description, issueType);
        JsonPath jsPathCreateIssue = new JsonPath(createIssueResponse);

        String issueId = jsPathCreateIssue.getString("id");
        String issueKey = jsPathCreateIssue.getString("key");

        IssueRestApi.deleteIssue(sessFilter, issueKey);

        IssueRestApi.getIssue404Error(sessFilter, issueKey);
    }
}
