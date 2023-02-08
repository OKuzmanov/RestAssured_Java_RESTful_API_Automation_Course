package com.rahulshettyacademy.rest.api;

import com.rahulshettyacademy.JsonText;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.given;

public class CommentRestApi extends BaseRestApi{
    public static String addComment(String sessName, String sessId, String issueKey, String comment) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("cookie", sessName + "=" + sessId)
                .body(JsonText.addCommentPayLoad(comment))
                .pathParam("issueKey", issueKey)
                .when().post(BASIC_URL + ADD_COMMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(201)
                .extract().response().body().asString();
    }

    public static String addComment(SessionFilter sessFilter, String issueKey, String comment) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonText.addCommentPayLoad(comment))
                .filter(sessFilter)
                .pathParam("issueKey", issueKey)
                .when().post(BASIC_URL + ADD_COMMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(201)
                .extract().response().body().asString();
    }

    public static String getComment(String sessName, String sessId, String issueKey, String commentId) {
        return given().log().all()
                .header("cookie", sessName + "=" + sessId)
                .pathParams("issueKey", issueKey, "commentId", commentId)
                .when().get(BASIC_URL + GET_PUT_DELETE_COMMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().asString();
    }

    public static String getComment(SessionFilter sessFilter, String issueKey, String commentId) {
        return given().log().all()
                .filter(sessFilter)
                .pathParams("issueKey", issueKey, "commentId", commentId)
                .when().get(BASIC_URL + GET_PUT_DELETE_COMMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().asString();
    }

    public static void getComment404Error(String sessName, String sessId, String issueKey, String commentId) {
        given().log().all()
                .header("cookie", sessName + "=" + sessId)
                .pathParams("issueKey", issueKey, "commentId", commentId)
                .when().get(BASIC_URL + GET_PUT_DELETE_COMMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(404);
    }

    public static void getComment404Error(SessionFilter sessFilter, String issueKey, String commentId) {
        given().log().all()
                .filter(sessFilter)
                .pathParams("issueKey", issueKey, "commentId", commentId)
                .when().get(BASIC_URL + GET_PUT_DELETE_COMMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(404);
    }

    public static String updateComment(String sessName, String sessId, String issueKey, String commentId, String newComment) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("cookie", sessName + "=" + sessId)
                .body(JsonText.editCommentPayLoad(newComment))
                .pathParams("issueKey", issueKey, "commentId", commentId)
                .when().put(BASIC_URL + GET_PUT_DELETE_COMMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().asString();
    }

    public static String updateComment(SessionFilter sessFilter, String issueKey, String commentId, String newComment) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .filter(sessFilter)
                .body(JsonText.editCommentPayLoad(newComment))
                .pathParams("issueKey", issueKey, "commentId", commentId)
                .when().put(BASIC_URL + GET_PUT_DELETE_COMMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().asString();
    }

    public static void deleteComment(String sessName, String sessId, String issueKey, String commentId) {
        given().log().all()
                .header("cookie", sessName + "=" + sessId)
                .pathParams("issueKey", issueKey, "commentId", commentId)
                .when().delete(BASIC_URL + GET_PUT_DELETE_COMMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(204);
    }

    public static void deleteComment(SessionFilter sessFilter, String issueKey, String commentId) {
        given().log().all()
                .filter(sessFilter)
                .pathParams("issueKey", issueKey, "commentId", commentId)
                .when().delete(BASIC_URL + GET_PUT_DELETE_COMMENT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(204);
    }
}
