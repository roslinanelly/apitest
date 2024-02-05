package petEndpoint.addPet;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import petEndpoint.BaseTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class AddNewPetTest extends BaseTest {

    /** positive test
     * Create new pet with simple details, all optional fields are provided
     * expected to return 200
     */
    @Test
    public void givenValidSimpleDetailsRequest_addNewPet_thenSuccess200_withResponseBodyMatched() throws IOException, JSONException {

        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_SimplePetDetails_200.json")))
                .replace("\"<petId>\"", "112233")
                ;

        String actualResponse =
            given()
                    .contentType("application/json")
                    .body(request)
                    .log().all()
                    .post()
                    .then()
                    .statusCode(200)
                    .extract().asString()

        ;

        JSONAssert.assertEquals(actualResponse, request, JSONCompareMode.NON_EXTENSIBLE);

    }

    /** positive test
     * Create new pet with minimum details, all optional fields are not provided
     * expected to return 200
     */
    @Test
    public void givenValidMinimumDetailsRequest_addNewPet_thenSuccess200_withResponseBodyMatched() throws IOException, JSONException {

        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_MinimumPetDetails_200.json")))
                .replace("\"<petId>\"", "112233")
                ;

        String actualResponse =
                given()
                        .contentType("application/json")
                        .body(request)
                        .log().all()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract().asString()

                ;

        JSONAssert.assertEquals(actualResponse, request, JSONCompareMode.NON_EXTENSIBLE);

    }

    /** positive test
     * Create new pet with complex details, multiple array values are provided
     * expected to return 200
     */
    @Test
    public void givenValidComplexDetailsRequest_addNewPet_thenSuccess200_withResponseBodyMatched() throws IOException, JSONException {

        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_ComplexPetDetails_200.json")))
                .replace("\"<petId>\"", "11223344")
                ;

        String actualResponse =
                given()
                        .contentType("application/json")
                        .body(request)
                        .log().all()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract().asString()

                ;

        JSONAssert.assertEquals(actualResponse, request, JSONCompareMode.NON_EXTENSIBLE);

    }

    /** negative test
     * Create new pet with missing mandatory details (name ond photoUrls)
     * expected to return 400 or 405
     * !!! currently return 200 - test failure
     */
    @Test
    public void givenInValidComplexDetailsRequest_addNewPet_thenSuccess200_withResponseBodyMatched() throws IOException, JSONException {

        String request = new String(Files.readAllBytes(Paths.get(resourcePath + "Request_MissingMandatory.json")))
                .replace("\"<petId>\"", "1122334455")
                ;

        given()
                .contentType("application/json")
                .body(request)
                .log().all()
                .post()
                .then()
                .statusCode(400)
                .log().all()

                ;

    }

}
