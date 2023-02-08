package com.rahulshettyacademy;


import com.rahulshettyacademy.pojo.getcoursesapiobjects.Course;
import com.rahulshettyacademy.pojo.getcoursesapiobjects.GetCourses;
import io.restassured.parsing.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

/**
 * Using the rahul shetty academy's OAuth 2.0 getCourse.php API endpoint.
 */
public class DeserializationTests {
    private final String BASE_URL = "https://rahulshettyacademy.com";
    private final String GET_COURSES_RESOURCE = "/getCourse.php";
    private String accessToken = "ya29.a0AVvZVspObJ0mIYmv4LgX1P_nZieFnLqUorG1tQBagjo5Z5vmG0Bzah9tL-DKdiORNuqP4VTUzJimEHFw_p5birB9eZ2y499BzXddxI8ZDEFAi44j7e1YQxO8GXtflp-bqsJ-zXbhT5E4DcpfcjSmB8liZWnLeQaCgYKAeYSARISFQGbdwaITVAaAGTDsEgSo2gcKIzoSg0165";

    private String expectedInstructor = "RahulShetty";
    private String expectedUrl = "rahulshettycademy.com";
    private String expectedServices = "projectSupport";
    private String expectedExpertise = "Automation";
    private String expectedLinkedIn = "https://www.linkedin.com/in/rahul-shetty-trainer/";
    private int expectedWebAutCoursesCount = 3;
    private int expectedApiCoursesCount = 2;
    private int expectedMobileCoursesCount = 1;

    @Test
    public void getListOfCourses() {
        GetCourses getCoursesPojo = given().log().all()
                .queryParam("access_token", accessToken)
                .expect().defaultParser(Parser.JSON)
                .when().get(BASE_URL + GET_COURSES_RESOURCE)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().body().as(GetCourses.class);

        Assert.assertEquals(getCoursesPojo.getInstructor(), expectedInstructor);
        Assert.assertEquals(getCoursesPojo.getUrl(), expectedUrl);
        Assert.assertEquals(getCoursesPojo.getServices(), expectedServices);
        Assert.assertEquals(getCoursesPojo.getExpertise(), expectedExpertise);
        Assert.assertEquals(getCoursesPojo.getLinkedIn(), expectedLinkedIn);
        Assert.assertEquals(getCoursesPojo.getCourses().getWebAutomation().size(), expectedWebAutCoursesCount);
        Assert.assertEquals(getCoursesPojo.getCourses().getApi().size(), expectedApiCoursesCount);
        Assert.assertEquals(getCoursesPojo.getCourses().getMobile().size(), expectedMobileCoursesCount);

        //Find the course price for a given title.
        ArrayList<Course> allCourses = new ArrayList<>();
        allCourses.addAll(getCoursesPojo.getCourses().getWebAutomation());
        allCourses.addAll(getCoursesPojo.getCourses().getApi());
        allCourses.addAll(getCoursesPojo.getCourses().getMobile());

        for (Course course : allCourses) {
            if (course.getCourseTitle().equals("Rest Assured Automation using Java")){
                System.out.println(course.getPrice());
                break;
            }
        }

        //Print all course titles in "webAutomation".
        ArrayList<Course> webAutomationCourses = getCoursesPojo.getCourses().getWebAutomation();
        for (Course course : webAutomationCourses) {
            System.out.println(course.getCourseTitle());
        }

        //Assert actual titles match with expected titles.
        Assert.assertEquals(getCoursesPojo.getCourses().getWebAutomation().get(0).getCourseTitle(), "Selenium Webdriver Java");
        Assert.assertEquals(getCoursesPojo.getCourses().getWebAutomation().get(1).getCourseTitle(), "Cypress");
        Assert.assertEquals(getCoursesPojo.getCourses().getWebAutomation().get(2).getCourseTitle(), "Protractor");
        Assert.assertEquals(getCoursesPojo.getCourses().getApi().get(0).getCourseTitle(), "Rest Assured Automation using Java");
        Assert.assertEquals(getCoursesPojo.getCourses().getApi().get(1).getCourseTitle(), "SoapUI Webservices testing");
        Assert.assertEquals(getCoursesPojo.getCourses().getMobile().get(0).getCourseTitle(), "Appium-Mobile Automation using Java");
    }
}
