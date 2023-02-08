package com.rahulshettyacademy;

import com.rahulshettyacademy.rest.api.IssueRestApi;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateIssueTests extends BaseTest {

    @Test
    public void test_createIssueValidData() {
        String summary = "Issue from RestAssured.";
        String description = "Description form RestAssured.";
        String issueType = "Task";

        String createIssueResponse = IssueRestApi.createIssue(sessFilter, projectKey, summary, description, issueType);
        JsonPath jsPathCreateIssue = new JsonPath(createIssueResponse);

        String issueId = jsPathCreateIssue.getString("id");
        String issueKey = jsPathCreateIssue.getString("key");

        String getRequestBody = IssueRestApi.getIssue(sessFilter, issueKey);
        JsonPath jspGetIssue = new JsonPath(getRequestBody);

        String actualId = jspGetIssue.getString("id");
        String actualKey = jspGetIssue.getString("key");
        String actualSummary = jspGetIssue.getString("fields.summary");

        Assert.assertEquals(actualId, issueId);
        Assert.assertEquals(actualKey, issueKey);
        Assert.assertEquals(actualSummary, summary);
    }
}
