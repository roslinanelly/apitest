package petEndpoint.regressionTest.updatePet;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import petEndpoint.regressionTest.BaseTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;

public class UpdatePetDetailsTest extends BaseTest {

    private static final String petId = "3333333333" ;

    /** positive test
     * Update existing pet details from simple(single array value) details to complex (multiple array values) details
     * expected to return 200
     */
    @Test
    public void givenValidComplexDetailsRequest_updateSimplePetDetails_thenSuccess200_withResponseBodyMatched() throws IOException, JSONException {

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

        request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_ComplexPetDetails_200.json")))
                .replace("\"<petId>\"", petId)
                ;

        String actualResponse =
                given()
                        .contentType("application/json")
                        .body(request)
                        .log().all()
                        .put()
                        .then()
                        .statusCode(200)
                        .extract().asString()

                ;

        JSONAssert.assertEquals(actualResponse, request, JSONCompareMode.NON_EXTENSIBLE);

    }


    /** positive test
     * Update existing pet details from complex (multiple array values) details to minimum details (mandatory fields only)
     * expected to return 200
     */
    @Test
    public void givenValidMinimumDetailsRequest_updateComplexPetDetails_thenSuccess200_withResponseBodyMatched() throws IOException, JSONException {

        // create the petId
        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_ComplexPetDetails_200.json")))
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

        request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_MinimumPetDetails_200.json")))
                .replace("\"<petId>\"", petId)
        ;

        String actualResponse =
                given()
                        .contentType("application/json")
                        .body(request)
                        .log().all()
                        .put()
                        .then()
                        .statusCode(200)
                        .extract().asString()

                ;

        JSONAssert.assertEquals(actualResponse, request, JSONCompareMode.NON_EXTENSIBLE);

    }

    /** negative test
     * Update existing pet details from complex (multiple array values) details to missing mandatory fields (name and photoUrls)
     * expected to return 405 (?)(according to swagger)
     * !!! currently returning 200 - test failure
     */
    @Test
    public void givenInvalidMissingDetailsRequest_updateComplexPetDetails_thenError405() throws IOException {

        // create the petId
        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_ComplexPetDetails_200.json")))
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

        request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_MissingMandatory.json")))
                .replace("\"<petId>\"", petId)
        ;

        given()
            .contentType("application/json")
            .body(request)
            .log().all()
            .put()
            .then()
            .statusCode(405)
        ;

    }

    /** negative test
     * Update existing pet details from complex (multiple array values) details to invalid value
     * expected to return 400
     */
    @Test
    public void givenInvalidDetailsRequest_updateComplexPetDetails_thenBadRequest400() throws IOException {

        // create the petId
        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_ComplexPetDetails_200.json")))
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

        // invalid data type of petId
        request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_SimplePetDetails_200.json")))
                .replace("\"<petId>\"", "abc")
        ;

        given()
                .contentType("application/json")
                .body(request)
                .log().all()
                .put()
                .then()
                .statusCode(400)
        ;

    }

    /** negative test
     * Update non-existing pet details
     * expected to return 404
     * !!! currently returning 200 - test failure
     */
    @Test
    public void givenNotExistPetDetailRequest_updatePetDetails_thenNotFound404() throws IOException {

        // create the petId
        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_ComplexPetDetails_200.json")))
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

        // delete it after creating it to make sure it is deleted
        given()
                .basePath(PETSTORE_URL + "/" + petId)
                .log().all()
                .delete()
                .then()
                .statusCode(200)

        ;

        // ensure delete worked and pet is not found
        given()
                .basePath(PETSTORE_URL + "/" + petId)
                .log().all()
                .get()
                .then()
                .statusCode(404)
                .body(not(Matchers.empty()))
                .contentType("application/json")
        ;

        request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_SimplePetDetails_200.json")))
                .replace("\"<petId>\"", petId)
        ;

        given()
                .contentType("application/json")
                .body(request)
                .log().all()
                .put()
                .then()
                .statusCode(400)
        ;

    }

}
