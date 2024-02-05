package petEndpoint.findPet;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import petEndpoint.BaseTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;


public class FindByIdTest extends BaseTest {

    private static final String petId = "999999999" ;
    private String request;

    /**
     * ensure the petId exists before each test
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
     * Find a pet with specific id
     * expected to return 200 when the petId exists
     * expected response body = request body
     */

    @Test
    public void givenExistingPetId_findPetDetailsById_thenSuccess200_withResponseBodyMatched() throws JSONException, IOException {

        String actualResponse =
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

        JSONAssert.assertEquals(actualResponse, request, JSONCompareMode.NON_EXTENSIBLE);

    }

    /** negative test
     * Find a pet that id does not exist (was deleted)
     * expected to return 404, when no status, according to swagger (status is mandatory)
     */
    @Test
    public void givenNotExistingPetId_findPetDetailsById_thenNotFound404_withResponseBodyMatched() throws IOException, JSONException {

        // make sure to delete petId before searching it
        given()
                .basePath(PETSTORE_URL + "/" + petId)
                .log().all()
                .delete()
                .then()
                .statusCode(200)
                ;

        String actualResponse =
                given()
                    .basePath(PETSTORE_URL + "/" + petId)
                    .log().all()
                    .get()
                    .then()
                    .statusCode(404)
                    .body(not(Matchers.empty()))
                    .contentType("application/json")
                    .extract().asString()
                ;

        String expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Response_PetNotFoundError_404.json")));

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

    }

}
