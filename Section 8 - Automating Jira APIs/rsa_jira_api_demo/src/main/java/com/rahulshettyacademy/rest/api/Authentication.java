package com.rahulshettyacademy.rest.api;

import com.rahulshettyacademy.JsonText;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.given;

public class Authentication extends BaseRestApi {
    public static String login(String username, String password) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonText.loginPayload(username, password))
                .when()
                .post(BASIC_URL + LOGIN_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().asString();
    }

    public static String login(String username, String password, SessionFilter sessFilter) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonText.loginPayload(username, password))
                .filter(sessFilter)
                .when()
                .post(BASIC_URL + LOGIN_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().asString();
    }
}
