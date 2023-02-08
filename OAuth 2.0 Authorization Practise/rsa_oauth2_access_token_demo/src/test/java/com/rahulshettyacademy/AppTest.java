package com.rahulshettyacademy;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private final String BASE_URL = "https://rahulshettyacademy.com";
    private final String GET_COURSES_RESOURCE = "/getCourse.php";

    private String authUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
            "?scope=https://www.googleapis.com/auth/userinfo.email" +
            "&auth_url=https://accounts.google.com/o/oauth2/v2/auth" +
            "&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com" +
            "&response_type=code" +
            "&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
    private String accessTokenUrl = "https://www.googleapis.com/oauth2/v4/token";
    private String grantType = "authorization_code";
    private String redirectUri = "https://rahulshettyacademy.com/getCourse.php";
    private String clientSecret = "erZOWM9g3UtwNRj340YYaK_W";
    private String clientId = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";

    private String userEmail = "oleg.kuzmanov97@gmail.com";
    private String pass = "xxxxxxx";

    private String authCode;
    private String accessToken;

    @BeforeTest
    public void initializeAccessToken() {

        //Deprecated from Google
//        WebDriverManager.chromedriver().setup();
//        WebDriver driver = new ChromeDriver();
//
//        driver.get(authUrl);
//
//        driver.findElement(By.cssSelector("input#identifierId")).sendKeys(userEmail);
//        driver.findElement(By.cssSelector("button.VfPpkd-LgbsSe.VfPpkd-LgbsSe-OWXEXe-k8QpJ")).click();
//
//        driver.findElement(By.cssSelector("input[type='password']]")).sendKeys(pass);
//        driver.findElement(By.cssSelector("button.VfPpkd-LgbsSe.VfPpkd-LgbsSe-OWXEXe-k8QpJ")).click();
//
//        String currentUrl = driver.getCurrentUrl();
//
//        authCode = Util.extractAccessToken(currentUrl);

        authCode = Util.extractAccessToken("https://rahulshettyacademy.com/getCourse.php?code=4%2F0AWtgzh7hHeRkjtm8_SJPwbDcHJpTcyciREgcY97VrolZgvpvVKGoU4nYOhSodVQ6c7x29g&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none");

        JsonPath jspPostAccessToken = given().log().all()
                .urlEncodingEnabled(false)
                .queryParams("code", authCode,
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "redirect_uri", redirectUri,
                        "grant_type", grantType)
                .when().post(accessTokenUrl)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().jsonPath();

        accessToken = jspPostAccessToken.getString("access_token");
    }

    @Test
    public void getListOfCourses() {
        JsonPath jspAccessToken = given().log().all()
                .queryParam("access_token", accessToken)
                .when().get(BASE_URL + GET_COURSES_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().jsonPath();
        String instructor = jspAccessToken.getString("instructor");
        Assert.assertEquals(instructor, "RahulShetty");

        String url = jspAccessToken.getString("url");
        Assert.assertEquals(url, "rahulshettycademy.com");

        String services = jspAccessToken.getString("services");
        Assert.assertEquals(services, "projectSupport");

        String expertise = jspAccessToken.getString("expertise");
        Assert.assertEquals(expertise, "Automation");

        String linkedIn = jspAccessToken.getString("linkedIn");
        Assert.assertEquals(linkedIn, "https://www.linkedin.com/in/rahul-shetty-trainer/");

        int allCourses = jspAccessToken.getInt("courses.size()");
        Assert.assertEquals(allCourses, 3);

        int webAutCourses = jspAccessToken.getInt("courses.webAutomation.size()");
        Assert.assertEquals(webAutCourses, 3);

        int apiCourses = jspAccessToken.getInt("courses.api.size()");
        Assert.assertEquals(apiCourses, 2);

        int mobileCourses = jspAccessToken.getInt("courses.mobile.size()");
        Assert.assertEquals(mobileCourses, 1);

        //.body() assertions throw errors
//        given().log().all()
//                .queryParam("access_token", accessToken)
//                .expect().defaultParser(Parser.JSON)
//                .when().get(BASE_URL + GET_COURSES_RESOURCE)
//                .then().log().all()
//                .assertThat().statusCode(200)
//                .body("instructor", equalTo("RahulShetty"))
//                .body("url", equalTo("rahulshettycademy"))
//                .body("services", equalTo("projectSupport"))
//                .body("expertise", equalTo("Automation"))
//                .body("courses.size()", equalTo(3))
//                .body("courses.webAutomation.size()", equalTo(3))
//                .body("courses.api.size()", equalTo(2))
//                .body("courses.mobile.size()", equalTo(1))
//                .body("linkedIn", equalTo("https://www.linkedin.com/in/rahul-shetty-trainer/"));
    }
}
