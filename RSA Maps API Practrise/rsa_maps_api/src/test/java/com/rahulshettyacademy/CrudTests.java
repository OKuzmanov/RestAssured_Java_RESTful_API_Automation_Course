package com.rahulshettyacademy;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * Unit test for simple App.
 */
public class CrudTests {
    final String QUERY_PARAM_KEY = "key";
    final String QUERY_PARAM_VALUE = "qaclick123";
    final String HEAD_KEY = "Content-Type";
    final String HEAD_VALUE = "application/json";
    final String BASE_URL = "https://rahulshettyacademy.com";
    final String CREATE_PLACE_RESOURCE = "/maps/api/place/add/json";
    final String GET_PLACE_RESOURCE = "/maps/api/place/get/json";
    final String UPDATE_PLACE_RESOURCE = "/maps/api/place/update/json";
    final String DELETE_PLACE_RESOURCE = "/maps/api/place/delete/json";

    @Test
    public void test_createPlaceOnMapAndGetIt() {

        String responseBody = given().log().all()
                .queryParam(QUERY_PARAM_KEY, QUERY_PARAM_VALUE)
                .header(HEAD_KEY, HEAD_VALUE)
                .body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -38.383494,\n" +
                        "    \"lng\": 33.427362\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Rahul Shetty Academy\",\n" +
                        "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                        "  \"address\": \"Gen. Yosif Gurko 50, 1520 Busmantsi\",\n" +
                        "  \"types\": [\n" +
                        "    \"education\",\n" +
                        "    \"institute\",\n" +
                        "    \"academy\"\n" +
                        "  ],\n" +
                        "  \"website\": \"https://rahulshettyacademy.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}")
                .when().post(BASE_URL + CREATE_PLACE_RESOURCE)
                .then().assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.41 (Ubuntu)")
                .header("Content-Type", "application/json;charset=UTF-8")
                .extract().response().body().asString();

        JsonPath js = new JsonPath(responseBody);
        String expectedPlace_id = js.getString("place_id");

        given().log().all()
                .queryParam(QUERY_PARAM_KEY, QUERY_PARAM_VALUE)
                .queryParam("place_id", expectedPlace_id)
                .when().get(BASE_URL + GET_PLACE_RESOURCE)
                .then().assertThat().statusCode(200).body("address", equalTo("Gen. Yosif Gurko 50, 1520 Busmantsi"));

    }

    @Test
    public void test_updatePlaceOnMap() {

        String responseBody = given().log().all()
                .queryParam(QUERY_PARAM_KEY, QUERY_PARAM_VALUE)
                .header(HEAD_KEY, HEAD_VALUE)
                .body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -38.383494,\n" +
                        "    \"lng\": 33.427362\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Rahul Shetty Academy\",\n" +
                        "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                        "  \"address\": \"Gen. Yosif Gurko 50, 1520 Busmantsi\",\n" +
                        "  \"types\": [\n" +
                        "    \"education\",\n" +
                        "    \"institute\",\n" +
                        "    \"academy\"\n" +
                        "  ],\n" +
                        "  \"website\": \"https://rahulshettyacademy.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}")
                .when().post(BASE_URL + CREATE_PLACE_RESOURCE)
                .then().assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.41 (Ubuntu)")
                .header("Content-Type", "application/json;charset=UTF-8")
                .extract().response().body().asString();

        JsonPath js = new JsonPath(responseBody);
        String expectedPlace_id = js.getString("place_id");

        String newAddress = "70, Summer Walk, California";

        given().log().all()
                .queryParam(QUERY_PARAM_KEY, QUERY_PARAM_VALUE)
                .header(HEAD_KEY, HEAD_VALUE)
                .body("{\n" +
                        "\"place_id\":\"" + expectedPlace_id + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put(BASE_URL + UPDATE_PLACE_RESOURCE)
                .then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        given().log().all()
                .queryParam(QUERY_PARAM_KEY, QUERY_PARAM_VALUE)
                .queryParam("place_id", expectedPlace_id)
                .when().get(BASE_URL + GET_PLACE_RESOURCE)
                .then().assertThat().statusCode(200).body("address", equalTo(newAddress));

    }

    @Test
    public void test_updateNonExistingPlaceOnMap() {

        int expectedPlace_id = new Random().nextInt();
        String newAddress = "70, Summer Walk, California";

        given().log().all()
                .queryParam(QUERY_PARAM_KEY, QUERY_PARAM_VALUE)
                .header(HEAD_KEY, HEAD_VALUE)
                .body("{\n" +
                        "\"place_id\":\"" + expectedPlace_id + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put(BASE_URL + UPDATE_PLACE_RESOURCE)
                .then().assertThat()
                .statusCode(404)
                .body("msg", equalTo("Update address operation failed, looks like the data doesn't exists"));
    }

    @Test
    public void test_deletePlace() {

        String responseBody = given().log().all()
                .queryParam(QUERY_PARAM_KEY, QUERY_PARAM_VALUE)
                .header(HEAD_KEY, HEAD_VALUE)
                .header(HEAD_KEY, HEAD_VALUE)
                .body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -38.383494,\n" +
                        "    \"lng\": 33.427362\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Rahul Shetty Academy\",\n" +
                        "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                        "  \"address\": \"Gen. Yosif Gurko 50, 1520 Busmantsi\",\n" +
                        "  \"types\": [\n" +
                        "    \"education\",\n" +
                        "    \"institute\",\n" +
                        "    \"academy\"\n" +
                        "  ],\n" +
                        "  \"website\": \"https://rahulshettyacademy.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}")
                .when().post(BASE_URL + CREATE_PLACE_RESOURCE)
                .then().assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.41 (Ubuntu)")
                .header("Content-Type", "application/json;charset=UTF-8")
                .extract().response().body().asString();

        JsonPath js = new JsonPath(responseBody);
        String expectedPlace_id = js.getString("place_id");

        given()
                .queryParam(QUERY_PARAM_KEY, QUERY_PARAM_VALUE)
                .header(HEAD_KEY, HEAD_VALUE)
                .body("{\n" +
                        "    \"place_id\":\"" + expectedPlace_id + "\"\n" +
                        "}\n")
                .when().delete(BASE_URL + DELETE_PLACE_RESOURCE)
                        .then().assertThat().statusCode(200).body("status", equalTo("OK"));

        given().log().all()
                .queryParam(QUERY_PARAM_KEY, QUERY_PARAM_VALUE)
                .queryParam("place_id", expectedPlace_id)
                .when().get(BASE_URL + GET_PLACE_RESOURCE)
                .then().assertThat()
                .statusCode(404)
                .body("msg", equalTo("Get operation failed, looks like place_id  doesn't exists"));

    }

    @Test
    public void test_deleteNonExistingPlace() {

        int expectedPlace_id = new Random().nextInt();

        given()
                .queryParam(QUERY_PARAM_KEY, QUERY_PARAM_VALUE)
                .header(HEAD_KEY, HEAD_VALUE)
                .body("{\n" +
                        "    \"place_id\":\"" + expectedPlace_id + "\"\n" +
                        "}\n")
                .when().delete(BASE_URL + DELETE_PLACE_RESOURCE)
                .then().assertThat()
                .statusCode(404)
                .body("msg", equalTo("Delete operation failed, looks like the data doesn't exists"));

    }
}
