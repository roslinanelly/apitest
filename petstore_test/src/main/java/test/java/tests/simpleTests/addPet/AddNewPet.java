package test.java.tests.simpleTests.addPet;

import org.testng.annotations.Test;
import test.java.tests.BaseTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class AddNewPet extends BaseTest {

    @Test
    public void givenValidMinDetailsRequest_addNewPet_thenSuccess200() throws IOException {

        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_SimplePetDetails_200.json")))
                .replace("\"<petId>\"", "112233")
                ;

        given()
                .baseUri(BASE_URI)
                .basePath(PETSTORE_URL)
                .contentType("application/json")
                .body(request)
                .log().all()
                .post()
                .then()
                .statusCode(200)
                .log().all()
                ;

    }

}
