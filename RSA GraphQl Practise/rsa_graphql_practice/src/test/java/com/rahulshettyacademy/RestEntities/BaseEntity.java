package com.rahulshettyacademy.RestEntities;

import static io.restassured.RestAssured.given;

public class BaseEntity {

    protected static String getGenericRequest(String body) {
        return given()
                .body(body)
                .when().post()
                .then().statusCode(200)
                .extract().response().body().asString();
    }
}
