package com.rahulshettyacademy;

import com.rahulshettyacademy.pojo.jiraapiobjects.CreateProjectPojo;
import com.rahulshettyacademy.pojo.jiraapiobjects.LoginPojo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * Using the rahul shetty academy's maps API endpoints.
 */
public class SerializationTests {

    protected static String BASIC_URL = "http://localhost:8080";
    protected static String LOGIN_RESOURCE = "/rest/auth/1/session";
    protected static String CREATE_PROJECT_RESOURCE = "/rest/api/2/project";
    protected static String DELETE_PROJECT_RESOURCE = "/rest/api/2/project/{projectKey}";
    private SessionFilter sessFilter;
    private RequestSpecification req;
    private ResponseSpecification resp;

    @BeforeTest
    public void setup() {
        sessFilter = new SessionFilter();

        req = new RequestSpecBuilder()
                .setBaseUri(BASIC_URL)
                .addHeader("Content-Type", "application/json")
                .addFilter(sessFilter)
                .build();

        resp = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Content-Type", "application/json;charset=UTF-8")
//                .expectResponseTime(Matchers.lessThan(1000L))
                .build();
    }

    @Test
    public void test_createProjectInJira() {
        final String projectKey = "DSB";

        LoginPojo loginPojo = new LoginPojo("olegati1", "OlekKuzmanow1997!");

        given().log().all().spec(req)
                .body(loginPojo)
                .when().post(LOGIN_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .spec(resp)
                .extract().response().body().asString();

        CreateProjectPojo createProjectPojo = new CreateProjectPojo("Demo Spec Builders", projectKey);
        createProjectPojo.setDescription("Description is created from a pojo class");

        given().log().all()
                .spec(req)
                .body(createProjectPojo)
                .when().post(CREATE_PROJECT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(201)
                .spec(resp)
                .extract().response().body().asString();

        given().log().all()
                .spec(req)
                .pathParams("projectKey", projectKey)
                .when().delete(DELETE_PROJECT_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(204)
                .spec(resp);
    }
}
