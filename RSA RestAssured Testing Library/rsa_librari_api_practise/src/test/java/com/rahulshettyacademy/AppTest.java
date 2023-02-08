package com.rahulshettyacademy;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AppTest {
    private final String ADD_BOOK_RESOURCE = "/Library/Addbook.php";
    private final String GET_BOOK_BY_ID_RESOURCE = "/Library/GetBook.php";
    private final String DELETE_BOOK_RESOURCE = "/Library/DeleteBook.php";

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = "http://216.10.245.166";
    }

    @Test(dataProvider = "addBookWithCorrectData")
    public void test_AddBooks(String name, String isbn, String aisle, String author) {
        //Add Book
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonString.addBookPayload(name, isbn, aisle, author))
                .when().post(ADD_BOOK_RESOURCE)
                .then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
                .extract().response().asString();

        JsonPath jspCreate = new JsonPath(response);
        String bookId = jspCreate.getString("ID");

        //Get Book
        JsonPath jspGet = given().log().all()
                .queryParam("ID", bookId)
                .when().get(GET_BOOK_BY_ID_RESOURCE)
                .then().log().all().assertThat().statusCode(200)
                .extract().jsonPath();

        Assert.assertEquals(jspGet.getString("[0].book_name"), name);
        Assert.assertEquals(jspGet.getString("[0].isbn"), isbn);
        Assert.assertEquals(jspGet.getString("[0].aisle"), aisle);
    }

    @Test(dataProvider = "AddBookDuplicateID")
    public void test_AddBooksErrorAlreadyExisting(String name, String isbn, String aisle, String author) {
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonString.addBookPayload(name, isbn, aisle, author))
                .when().post(ADD_BOOK_RESOURCE)
                .then().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
                .extract().response().asString();

        given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonString.addBookPayload(name, isbn, aisle, author))
                .when().post(ADD_BOOK_RESOURCE)
                .then().assertThat()
                    .statusCode(404)
                    .body("msg", equalTo("Add Book operation failed, looks like the book already exists"));
    }

    @Test
    public void test_GetBookByNonExistentId() {
        given().log().all()
                .queryParam("ID", String.valueOf(Integer.MAX_VALUE) + "1")
                .when().post(GET_BOOK_BY_ID_RESOURCE)
                .then().assertThat()
                    .statusCode(404)
                    .body("msg", equalTo("The book by requested bookid / author name does not exists!"));
    }

    @Test
    public void test_GetBooksByAuthorName() {
        String authName = "Brie Daniels " + new Random().nextInt();
        int countBooksToAdd = 4;

        //Add Book
        for (int i = 0; i < countBooksToAdd; i++) {
            given().log().all()
                    .header("Content-Type", "application/json")
                    .body(JsonString.addBookPayload("Why Leaders Eat Last", "abd", String.valueOf(new Random().nextInt()), authName))
                    .when().post(ADD_BOOK_RESOURCE)
                    .then().assertThat().statusCode(200).body("Msg", equalTo("successfully added"));
        }

        //Get Book By Auth Name
        JsonPath jspGet = given().log().all()
                .queryParam("AuthorName", authName)
                .when().get(GET_BOOK_BY_ID_RESOURCE)
                .then().assertThat().statusCode(200)
                .extract().jsonPath();

        int bookCount = jspGet.getList("").size();
        Assert.assertEquals(bookCount, countBooksToAdd);
    }

    @Test
    public void test_GetBooksByNonExistentAuthorName() {
        String authName = "Brie Daniels " + new Random().nextInt();

        given().log().all()
                .queryParam("AuthorName", authName)
                .when().get(GET_BOOK_BY_ID_RESOURCE)
                .then().assertThat()
                    .statusCode(404)
                    .body("msg", equalTo("The book by requested bookid / author name does not exists!"));
    }

    @Test(dataProvider = "addBookWithCorrectData")
    public void test_DeleteBooks(String name, String isbn, String aisle, String author) {
        //Add Book
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonString.addBookPayload(name, isbn, aisle, author))
                .when().post(ADD_BOOK_RESOURCE)
                .then().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
                .extract().response().asString();

        JsonPath jspCreate = new JsonPath(response);
        String bookId = jspCreate.getString("ID");

        //Delete Book

        given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonString.deleteBookPayload(bookId))
                .given().post(DELETE_BOOK_RESOURCE)
                .then().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted"));


        //Get Book
        given().log().all()
                .queryParam("ID", bookId)
                .when().get(GET_BOOK_BY_ID_RESOURCE)
                .then().assertThat()
                    .statusCode(404)
                    .body("msg", equalTo("The book by requested bookid / author name does not exists!"));
    }

    @Test
    public void test_DeleteBooksNonExistentID() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body(JsonString.deleteBookPayload("aaa20193"))
                .given().post(DELETE_BOOK_RESOURCE)
                .then().assertThat()
                    .statusCode(404)
                    .body("msg", equalTo("Delete Book operation failed, looks like the book doesnt exists"));

    }

    @DataProvider
    public Object[][] addBookWithCorrectData() throws IOException {
        return ExcelDataDriver.getData("LibraryApiData", "AddBookWithCorrectData");
    }

    @DataProvider
    public Object[][] AddBookDuplicateID() throws IOException {
        return ExcelDataDriver.getData("LibraryApiData", "AddBookDuplicateID");
    }
}
