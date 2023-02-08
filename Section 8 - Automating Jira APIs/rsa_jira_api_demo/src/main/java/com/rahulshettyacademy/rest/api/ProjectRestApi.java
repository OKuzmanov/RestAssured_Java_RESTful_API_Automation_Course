package com.rahulshettyacademy.rest.api;

import com.rahulshettyacademy.JsonText;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.given;

public class ProjectRestApi extends BaseRestApi{

    public static String createProject(String sessName, String sessId, String projectName, String projectKey) {
        return given().log().all()
                .header("cookie", sessName + "=" + sessId)
                .header("Content-Type", "application/json")
                .body(JsonText.createProjectPayload(projectName, projectKey))
                .when()
                .post(BASIC_URL + CREATE_PROJECT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(201)
                .extract().response().body().asString();
    }

    public static String createProject(SessionFilter sessFilter, String projectName, String projectKey) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonText.createProjectPayload(projectName, projectKey))
                .filter(sessFilter)
                .when()
                .post(BASIC_URL + CREATE_PROJECT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(201)
                .extract().response().body().asString();
    }

    public static void deleteProject(String sessName, String sessId, String projectKey) {
        given().log().all()
                .header("cookie", sessName + "=" + sessId)
                .pathParam("projectKey", projectKey)
                .when().delete(BASIC_URL + DELETE_PROJECT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(204);
    }

    public static void deleteProject(SessionFilter sessFilter, String projectKey) {
        given().log().all()
                .filter(sessFilter)
                .pathParams("projectKey", projectKey)
                .when().delete(BASIC_URL + DELETE_PROJECT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(204);
    }
}
