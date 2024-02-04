package test.java.tests.simpleTests.deleteAPetRecord;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import test.java.tests.BaseTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class DeleteAPet extends BaseTest {

    private static final String petId = "1111111111" ;

    /** positive test
     * Delete existing Pet by Id
     * Not providing api-key
     * expected to return 200
     */
    @Test
    public void givenValidPetId_deleteAPetWithoutApiKey_thenSuccess200() throws IOException, JSONException {

        // create the petId
        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_SimplePetDetails_200.json")))
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

        // delete the created pet above
        String actualResponse =
                given()
                    .basePath(PETSTORE_URL + "/" + petId)
                    .log().all()
                    .delete()
                    .then()
                    .statusCode(200)
                    .extract().asString()

        ;

        String expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Response_PetDeleted_200.json")))
                .replace("<petId>", petId);

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

    }

    /** positive test
     * Delete existing Pet by Id
     * providing api-key
     * expected to return 200
     */
    @Test
    public void givenValidPetId_deleteAPetWithApiKey_thenSuccess200() throws IOException, JSONException {

        // create the petId
        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_SimplePetDetails_200.json")))
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

        // delete the created pet above
        String actualResponse =
                given()
                        .header("api_key", "REF" + petId)
                        .basePath(PETSTORE_URL + "/" + petId)
                        .log().all()
                        .delete()
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract().asString()

                ;

        String expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Response_PetDeleted_200.json")))
                .replace("<petId>", petId);

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

    }

    /** negative test
     * Delete existing Pet by Id that does not exist
     * expected to return 404
     */
    @Test
    public void givenValidPetId_deleteAPet_thenNotFound404() {

            given()
                    .basePath(PETSTORE_URL + "/" + petId)
                    .log().all()
                    .delete()
                    .then()
                    .statusCode(404)
                    .extract().asString()

            ;

    }

}
