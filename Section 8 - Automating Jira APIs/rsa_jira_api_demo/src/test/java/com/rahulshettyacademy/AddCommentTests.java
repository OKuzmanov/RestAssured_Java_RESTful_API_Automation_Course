package com.rahulshettyacademy;

import com.rahulshettyacademy.rest.api.CommentRestApi;
import com.rahulshettyacademy.rest.api.IssueRestApi;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddCommentTests extends  BaseTest {

    @Test
    public void test_addComment() {
        String summary = "Issue from RestAssured.";
        String description = "Description form RestAssured.";
        String issueType = "Task";

        String createIssueResponse = IssueRestApi.createIssue(sessFilter, projectKey, summary, description, issueType);
        JsonPath jsPathCreateIssue = new JsonPath(createIssueResponse);

        String issueId = jsPathCreateIssue.getString("id");
        String issueKey = jsPathCreateIssue.getString("key");

        String comment = "This is my first comment.";
        String addCommentRequest = CommentRestApi.addComment(sessFilter, issueKey, comment);
        JsonPath jspAddComment = new JsonPath(addCommentRequest);

        String commentId = jspAddComment.getString("id");

        String getCommentRequest = CommentRestApi.getComment(sessFilter, issueKey, commentId);
        JsonPath jspGetComment = new JsonPath(getCommentRequest);

        String actualBody = jspGetComment.getString("body");

        Assert.assertEquals(actualBody, comment);
    }
}
