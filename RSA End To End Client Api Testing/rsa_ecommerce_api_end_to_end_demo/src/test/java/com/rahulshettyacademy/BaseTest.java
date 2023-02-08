package com.rahulshettyacademy;

import com.rahulshettyacademy.pojo.AddProductPojo;
import com.rahulshettyacademy.pojo.CreateOrdersObj;
import com.rahulshettyacademy.pojo.LoginObj;
import com.rahulshettyacademy.pojo.RegisterObj;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;

public class BaseTest {
    protected String authToken;
    protected String userId;
    protected String productId;
    protected String orderId;
    protected final String BASIC_URL = "https://rahulshettyacademy.com";
    protected final String REGISTER_USER_RESOURCE = "/api/ecom/auth/register";
    protected final String LOGIN_USER_RESOURCE = "/api/ecom/auth/login";
    protected final String ADD_PRODUCT_RESOURCE = "/api/ecom/product/add-product";
    protected final String GET_ALL_PRODUCTS_RESOURCE = "/api/ecom/product/get-all-products";
    protected final String GET_ORDERS_FOR_CUSTOMER_RESOURCE = "/api/ecom/order/get-orders-for-customer/{userId}";
    protected final String CREATE_ORDER_RESOURCE = "/api/ecom/order/create-order";
    protected final String DELETE_ORDER_RESOURCE = "/api/ecom/order/delete-order/{orderId}";
    protected final String DELETE_PRODUCT_RESOURCE = "/api/ecom/product/delete-product/{productId}";

    @BeforeClass
    public void setup() {
        RequestSpecification customReqSpec = new RequestSpecBuilder()
                .setBaseUri(BASIC_URL)
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

    @BeforeClass(dependsOnMethods = {"setup"})
    public void regAndLogin() {
        RegisterObj registerObj = new RegisterObj();
        registerUser(registerObj);

        JsonPath logUserJsp = loginUser(new LoginObj(registerObj.getUserEmail(), registerObj.getUserPassword()));

        authToken = logUserJsp.getString("token");

        userId = logUserJsp.getString("userId");
    }

    protected void registerUser(RegisterObj registerObj) {
        given()
                .header("Content-Type", "application/json")
                .body(registerObj)
                .when().post(REGISTER_USER_RESOURCE)
                .then()
                .assertThat().statusCode(200)
                .body("message", Matchers.equalTo("Registered Successfully"));
    }

    protected JsonPath loginUser(LoginObj loginObj) {
        return given()
                .header("Content-Type", "application/json")
                .body(loginObj)
                .when().post(LOGIN_USER_RESOURCE)
                .then()
                .assertThat().statusCode(200)
                .body("message", Matchers.equalTo("Login Successfully"))
                .extract().response().body().jsonPath();
    }

    protected JsonPath getAllProducts(String authToken) {
        return given()
                .header("Authorization", authToken)
                .header("Content-Type", "Content-Type=application/x-www-form-urlencoded")
                .when().post(GET_ALL_PRODUCTS_RESOURCE)
                .then()
                .assertThat().statusCode(200)
                .body("message", Matchers.equalTo("All Products fetched Successfully"))
                .extract().response().body().jsonPath();
    }

    protected JsonPath addProduct(AddProductPojo productPojo) {
        return given()
                .header("Authorization", productPojo.getAuthorization())
                .param("productName", productPojo.getProductName())
                .param("productAddedBy", productPojo.getProductAddedBy())
                .param("productCategory", productPojo.getProductCategory())
                .param("productSubCategory", productPojo.getProductSubCategory())
                .param("productPrice", productPojo.getProductPrice())
                .param("productDescription", productPojo.getProductDescription())
                .param("productFor", productPojo.getProductFor())
                .multiPart("productImage", productPojo.getProductImage())
                .when().post(ADD_PRODUCT_RESOURCE)
                .then()
                .assertThat().statusCode(201)
                .body("message", Matchers.equalTo("Product Added Successfully"))
                .extract().response().body().jsonPath();
    }

    protected void deleteProduct(String authToken, String productId) {
        given()
                .header("Authorization", authToken)
                .pathParam("productId", productId)
                .when().delete(DELETE_PRODUCT_RESOURCE)
                .then()
                .assertThat().statusCode(200)
                .body("message", Matchers.equalTo("Product Deleted Successfully"));
    }

    protected JsonPath getAllOrdersForCustomer(String authToken, String userId) {
        return given()
                .header("Authorization", authToken)
                .pathParam("userId", userId)
                .when().get(GET_ORDERS_FOR_CUSTOMER_RESOURCE)
                .then()
                .assertThat().statusCode(200)
                .extract().response().body().jsonPath();
    }

    protected JsonPath createOrder(String authToken, CreateOrdersObj createOrdersObj) {
        return given()
                .headers("Authorization", authToken, "Content-Type", "application/json")
                .body(createOrdersObj)
                .when().post(CREATE_ORDER_RESOURCE)
                .then()
                .assertThat().statusCode(201)
                .body("message", Matchers.equalTo("Order Placed Successfully"))
                .extract().response().body().jsonPath();
    }

    protected void deleteOrder(String authToken, String orderId) {
        given()
                .header("Authorization", authToken)
                .pathParam("orderId", orderId)
                .when().delete(DELETE_ORDER_RESOURCE)
                .then()
                .assertThat().statusCode(200)
                .body("message", Matchers.equalTo("Orders Deleted Successfully"));
    }
}
