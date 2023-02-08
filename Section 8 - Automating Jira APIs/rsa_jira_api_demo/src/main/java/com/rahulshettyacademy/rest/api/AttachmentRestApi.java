package com.rahulshettyacademy.rest.api;

import io.restassured.filter.session.SessionFilter;

import java.io.File;

import static io.restassured.RestAssured.given;

public class AttachmentRestApi extends BaseRestApi {
    public static String addAttachment(SessionFilter sessFilter, String issueKey) {
        return given().log().all()
                .filter(sessFilter)
                .header("Content-Type", "multipart/form-data")
                .header("X-Atlassian-Token", "no-check")
                .multiPart("file", new File(System.getProperty("user.dir") + "\\resources\\Example Text File.txt"))
                .pathParam("issueKey", issueKey)
                .when().post(BASIC_URL + ADD_ATTACHMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().asString();
    }

    public static String getAttachmentMetadata(SessionFilter sessFilter, String attachmentId) {
        return given().log().all()
                .filter(sessFilter)
                .pathParam("attachmentId", attachmentId)
                .when().get(BASIC_URL + GET_DELETE_ATTACHMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().asString();
    }

    public static void getAttachmentStatus404(SessionFilter sessFilter, String attachmentId) {
        given().log().all()
                .filter(sessFilter)
                .pathParam("attachmentId", attachmentId)
                .when().get(BASIC_URL + GET_DELETE_ATTACHMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(404);
    }

    public static void deleteAttachment(SessionFilter sessFilter, String attachmentId) {
        given().log().all()
                .filter(sessFilter)
                .pathParam("attachmentId", attachmentId)
                .when().delete(BASIC_URL + GET_DELETE_ATTACHMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(204);
    }
}
