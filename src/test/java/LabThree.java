import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LabThree {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://405ade92-27f5-4949-904f-2fa8af29538c.mock.pstmn.io";
        RestAssured.requestSpecification = new io.restassured.builder.RequestSpecBuilder()
                .build();
    }

    @Test(priority = 1)
    public void testGetSuccess() {
        given()
            .header("x-mock-response-name", "Test Get Mock Success")
            .queryParam("name", " success")
        .when()
            .get("/")
        .then()
            .statusCode(200)
            .body(containsString("Kyrylo Andrushchak"));
    }

    @Test(priority = 2)
    public void testGetUnsuccess() {
        given()
            .header("x-mock-response-name", "Test Get Mock Unsuccess")
            .queryParam("name", " unsuccess")
        .when()
            .get("/")
        .then()
            .statusCode(404)
            .body(containsString("i won't say my name!"));
    }

    @Test(priority = 3)
    public void testPostSuccess() {
        given()
            .header("x-mock-response-name", "Test Post Mock 200")
            .header("Content-Type", "application/json")
            .body("{\"dummy\": \"data\"}")
            .queryParam("permission", " yes")
        .when()
            .post("/")
        .then()
            .statusCode(200);
    }

    @Test(priority = 4)
    public void testPostUnsuccess() {
        given()
            .header("x-mock-response-name", "Test Post Mock 400")
            .header("Content-Type", "application/json")
            .body("{\"dummy\": \"data\"}")
        .when()
            .post("/")
        .then()
            .statusCode(400)
            .body(containsString("You dont have a permission to create something"));
    }

    @Test(priority = 5)
    public void testPutError500() {
        given()
            .header("x-mock-response-name", "Test Put Mock 500")
            .header("Content-Type", "application/json")
            .body("{\n    \"name\": \"\",\n    \"surname\": \"\"\n}")
        .when()
            .put("/")
        .then()
            .statusCode(500);
    }

    @Test(priority = 6)
    public void testDelete() {
        given()
            .header("x-mock-response-name", "Test Delete")
            .queryParam("SessionID", " 123456789")
        .when()
            .delete("/")
        .then()
            .statusCode(200)
            .body(containsString("world"));
    }
}
