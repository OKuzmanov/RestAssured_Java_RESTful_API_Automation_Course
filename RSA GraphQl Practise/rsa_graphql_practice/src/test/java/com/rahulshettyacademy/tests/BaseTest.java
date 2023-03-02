package com.rahulshettyacademy.tests;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected final String BASIC_URL = "https://rahulshettyacademy.com/gq/graphql";
    protected final String errMsgExpected = "Item does not exist";

    @BeforeClass
    public void beforeTests() {
        RequestSpecification customReqSpec = new RequestSpecBuilder()
                .setBaseUri(BASIC_URL)
                .addHeader("Content-Type", "application/json")
                .log(LogDetail.ALL)
                .setRelaxedHTTPSValidation()
                .build();

        ResponseSpecification customRespSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .expectResponseTime(Matchers.lessThan(20000L))
                .build();

        RestAssured.requestSpecification = customReqSpec;
        RestAssured.responseSpecification = customRespSpec;
    }
}
