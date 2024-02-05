package regressionTest.updatePet;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import regressionTest.BaseTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;

public class UpdatePetByForm extends BaseTest {

    private static final String petId = "7777777777" ;
    private String request;

    /**
     * ensure the petId exists before running these tests
     */
    @BeforeEach
    public void prepareData() throws IOException {

        // create the petId
        request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_SimplePetDetails_200.json")))
                .replace("\"<petId>\"", petId)
        ;

        given()
                .contentType("application/json")
                .body(request)
                .log().all()
                .post()
                .then()
                .statusCode(200)
        ;
    }

    /** positive test
     * Update name and status of a pet with specific id
     * expected to return 200 when the petId exists
     */
    @Test
    public void givenExistingPetId_updateNameAndStatus_thenSuccess200_withResponseBodyMatched() throws JSONException, IOException {

        String actualResponse =
                given()
                        .basePath(PETSTORE_URL + "/" + petId)
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("name", "updateName Test1")
                        .formParam("status", "pending")
                        .log().all()
                        .post()
                        .then()
                        .statusCode(200)
                        .body(not(Matchers.empty()))
                        .extract().asString()
                ;

        String expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Response_Default_200.json")))
                .replace("<petId>", petId);

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

        //verify the pet details have been updated by calling GET
        actualResponse =
                given()
                        .basePath(PETSTORE_URL + "/" + petId)
                        .log().all()
                        .get()
                        .then()
                        .statusCode(200)
                        .body(not(Matchers.empty()))
                        .contentType("application/json")
                        .extract().asString()
                ;

        expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_SimplePetDetails_200.json")))
                .replace("\"<petId>\"", petId)
                .replace("simple", "updateName Test1")
                .replace("available", "pending")
                ;

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

    }


    /** positive test
     * Update name only of a pet with specific id
     * expected to return 200 when the petId exists
     */
    @Test
    public void givenExistingPetId_updateNameOnly_thenSuccess200_withResponseBodyMatched() throws JSONException, IOException {

        String actualResponse =
                given()
                        .basePath(PETSTORE_URL + "/" + petId)
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("name", "updateName Test1")
                        .log().all()
                        .post()
                        .then()
                        .statusCode(200)
                        .body(not(Matchers.empty()))
                        .extract().asString()
                ;

        String expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Response_Default_200.json")))
                .replace("<petId>", petId);

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

        //verify the pet details have been updated by calling GET
        actualResponse =
                given()
                        .basePath(PETSTORE_URL + "/" + petId)
                        .log().all()
                        .get()
                        .then()
                        .statusCode(200)
                        .body(not(Matchers.empty()))
                        .contentType("application/json")
                        .extract().asString()
        ;

        expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_SimplePetDetails_200.json")))
                .replace("\"<petId>\"", petId)
                .replace("simple", "updateName Test1")
        ;

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

    }

    /** positive test
     * Update name and status of a pet with specific id
     * expected to return 200 when the petId exists
     */
    @Test
    public void givenExistingPetId_updateStatusOnly_thenSuccess200_withResponseBodyMatched() throws JSONException, IOException {

        String actualResponse =
                given()
                        .basePath(PETSTORE_URL + "/" + petId)
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("status", "sold")
                        .log().all()
                        .post()
                        .then()
                        .statusCode(200)
                        .body(not(Matchers.empty()))
                        .extract().asString()
                ;

        String expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Response_Default_200.json")))
                .replace("<petId>", petId);

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

        //verify the pet details have been updated by calling GET
        actualResponse =
                given()
                        .basePath(PETSTORE_URL + "/" + petId)
                        .log().all()
                        .get()
                        .then()
                        .statusCode(200)
                        .body(not(Matchers.empty()))
                        .contentType("application/json")
                        .extract().asString()
        ;

        expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_SimplePetDetails_200.json")))
                .replace("\"<petId>\"", petId)
                .replace("available", "sold")
        ;

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

    }

}
