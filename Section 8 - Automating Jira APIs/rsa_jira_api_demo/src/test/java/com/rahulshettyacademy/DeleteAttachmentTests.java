package com.rahulshettyacademy;

import com.rahulshettyacademy.rest.api.AttachmentRestApi;
import com.rahulshettyacademy.rest.api.IssueRestApi;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class DeleteAttachmentTests extends BaseTest {

    @Test
    public void test_deleteAttachment() {
        String summary = "Issue from RestAssured.";
        String description = "Description form RestAssured.";
        String issueType = "Task";

        String createIssueResponse = IssueRestApi.createIssue(sessFilter, projectKey, summary, description, issueType);
        JsonPath jsPathCreateIssue = new JsonPath(createIssueResponse);

        String issueId = jsPathCreateIssue.getString("id");
        String issueKey = jsPathCreateIssue.getString("key");

        String addAttachResp = AttachmentRestApi.addAttachment(sessFilter, issueKey);
        JsonPath jspAddAttach = new JsonPath(addAttachResp);

        String attachmentId = jspAddAttach.getString("[0].id");
        String fileName = jspAddAttach.getString("[0].filename");

        AttachmentRestApi.deleteAttachment(sessFilter, attachmentId);

        AttachmentRestApi.getAttachmentStatus404(sessFilter, attachmentId);
    }
}
