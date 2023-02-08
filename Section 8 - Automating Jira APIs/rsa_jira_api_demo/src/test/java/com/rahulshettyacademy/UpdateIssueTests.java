package com.rahulshettyacademy;

import com.rahulshettyacademy.rest.api.IssueRestApi;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateIssueTests extends BaseTest {

    @Test
    public void test_updateIssueValidData() {
        String summary = "Issue from RestAssured.";
        String description = "Description form RestAssured.";
        String issueType = "Task";

        String createIssueResponse = IssueRestApi.createIssue(sessFilter, projectKey, summary, description, issueType);
        JsonPath jsPathCreateIssue = new JsonPath(createIssueResponse);

        String issueId = jsPathCreateIssue.getString("id");
        String issueKey = jsPathCreateIssue.getString("key");

        String newSummary = "Updated Summary from RestAssured.";
        String newDescription = "Updated Description form RestAssured.";

        IssueRestApi.updateIssue(sessFilter, newSummary, newDescription, issueKey);

        String getRequestBody = IssueRestApi.getIssue(sessFilter, issueKey);
        JsonPath jspGetIssue = new JsonPath(getRequestBody);

        String actualSummary = jspGetIssue.getString("fields.summary");
        String actualDescription = jspGetIssue.getString("fields.description");

        Assert.assertEquals(actualSummary, newSummary);
        Assert.assertEquals(actualDescription, newDescription);
    }
}
