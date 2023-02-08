package com.rahulshettyacademy.rest.api;

public class BaseRestApi {
    protected static String BASIC_URL = "http://localhost:8080";
    protected static String LOGIN_RESOURCE = "/rest/auth/1/session";
    protected static String ISSUE_RESOURCE = "/rest/api/2/issue/";
    protected static String GET_PUT_DELETE_ISSUE_RESOURCE = "/rest/api/2/issue/{issueKey}";
    protected static String ADD_COMMENT_RESOURCE = "/rest/api/2/issue/{issueKey}/comment";
    protected static String GET_PUT_DELETE_COMMENT_RESOURCE = "/rest/api/2/issue/{issueKey}/comment/{commentId}";
    protected static String ADD_ATTACHMENT_RESOURCE = "/rest/api/2/issue/{issueKey}/attachments";
    protected static String GET_DELETE_ATTACHMENT_RESOURCE = "/rest/api/2/attachment/{attachmentId}";
    protected static String CREATE_PROJECT_RESOURCE = "/rest/api/2/project";
    protected static String DELETE_PROJECT_RESOURCE = "/rest/api/2/project/{projectKey}";
}