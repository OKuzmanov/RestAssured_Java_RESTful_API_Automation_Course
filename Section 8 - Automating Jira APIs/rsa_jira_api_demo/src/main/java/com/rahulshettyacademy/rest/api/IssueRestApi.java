package com.rahulshettyacademy.rest.api;

import com.rahulshettyacademy.JsonText;
import io.restassured.filter.session.SessionFilter;

import java.io.File;

import static io.restassured.RestAssured.given;

public class IssueRestApi extends BaseRestApi {

    public static String createIssue(String sessName, String sessId, String projectKey, String summary, String description, String issueType) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("cookie", sessName + "=" + sessId)
                .body(JsonText.addIssuePayLoad(projectKey, summary, description, issueType))
                .when().post(BASIC_URL + ISSUE_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(201)
                .extract().response().body().asString();
    }

    public static String createIssue(SessionFilter sessFilter, String projectKey, String summary, String description, String issueType) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonText.addIssuePayLoad(projectKey, summary, description, issueType))
                .filter(sessFilter)
                .when().post(BASIC_URL + ISSUE_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(201)
                .extract().response().body().asString();
    }

    public static String getIssue(String sessName, String sessId, String issueKey) {
        return given().log().all()
                .header("cookie", sessName + "=" + sessId)
                .pathParam("issueKey", issueKey)
                .when().get(BASIC_URL + GET_PUT_DELETE_ISSUE_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().asString();
    }

    public static String getIssue(SessionFilter sessFilter, String issueKey) {
        return given().log().all()
                .filter(sessFilter)
                .pathParam("issueKey", issueKey)
                .when().get(BASIC_URL + GET_PUT_DELETE_ISSUE_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().asString();
    }

    public static void getIssue404Error(String sessName, String sessId, String issueKey) {
        given().log().all()
                .header("cookie", sessName + "=" + sessId)
                .pathParam("issueKey", issueKey)
                .when().get(BASIC_URL + GET_PUT_DELETE_ISSUE_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(404);
    }

    public static void getIssue404Error(SessionFilter sessFilter, String issueKey) {
        given().log().all()
                .filter(sessFilter)
                .pathParam("issueKey", issueKey)
                .when().get(BASIC_URL + GET_PUT_DELETE_ISSUE_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(404);
    }

    public static void updateIssue(String sessName, String sessId, String newSummary, String newDescription, String issueKey) {
        given().log().all()
                .header("Content-Type", "application/json")
                .header("cookie", sessName + "=" + sessId)
                .body(JsonText.editIssuePayLoad(newSummary, newDescription))
                .pathParam("issueKey", issueKey)
                .when().put(BASIC_URL + GET_PUT_DELETE_ISSUE_RESOURCE)
                .then().log().all().assertThat().statusCode(204);
    }

    public static void updateIssue(SessionFilter sessFilter, String newSummary, String newDescription, String issueKey) {
        given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonText.editIssuePayLoad(newSummary, newDescription))
                .filter(sessFilter)
                .pathParam("issueKey", issueKey)
                .when().put(BASIC_URL + GET_PUT_DELETE_ISSUE_RESOURCE)
                .then().log().all().assertThat().statusCode(204);
    }

    public static void deleteIssue(String sessName, String sessId, String issueKey) {
        given().log().all()
                .header("cookie", sessName + "=" + sessId)
                .pathParam("issueKey", issueKey)
                .when().delete(BASIC_URL + GET_PUT_DELETE_ISSUE_RESOURCE)
                .then().log().all().assertThat().statusCode(204);
    }

    public static void deleteIssue(SessionFilter sessFilter, String issueKey) {
        given().log().all()
                .filter(sessFilter)
                .pathParam("issueKey", issueKey)
                .when().delete(BASIC_URL + GET_PUT_DELETE_ISSUE_RESOURCE)
                .then().log().all().assertThat().statusCode(204);
    }
}
