package petEndpoint.findPet;

import org.junit.jupiter.api.Test;
import petEndpoint.BaseTest;

import static io.restassured.RestAssured.given;

public class FindByStatusTest extends BaseTest {

    private static final String objectName = "/findByStatus";

    /** positive test
     * Find all pets that are at "available" status
     * expected to return 200, even no pet with that status
     */

    @Test
    public void givenValidStatusAvailable_findAllPetDetailsByStatus_thenSuccess200() {

        given()
                .basePath(PETSTORE_URL + objectName)
                .queryParam("status", "available")
                .log().all()
                .get()
                .then()
                .statusCode(200);

    }

    /** positive test
     * Find all pets that are at "pending" status
     * expected to return 200, even no pet with that status
     */
    @Test
    public void givenValidStatusPending_findAllPetDetailsByStatus_thenSuccess200() {

        given()
                .basePath(PETSTORE_URL + objectName)
                .queryParam("status", "pending")
                .log().all()
                .get()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .log().all()
                ;


    }

    /** positive test
     * Find all pets that are at "sold" status
     * expected to return 200, even no pet with that status (empty array response)
     */
    @Test
    public void givenValidStatusSold_findAllPetDetailsByStatus_thenSuccess200() {

        given()
                .basePath(PETSTORE_URL + objectName)
                .queryParam("status", "sold")
                .log().all()
                .get()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .log().all()
                ;


    }

    /** negative test
     * Find all pets that are at invalid status i.e. not in (available, pending, sold)
     * expected to return 400, when invalid status, according to swagger
     * !!! This test has failure
     */
    @Test
    public void givenInvalidStatus_findAllPetDetailsByStatus_thenBadRequest400() {

        given()
                .basePath(PETSTORE_URL + objectName)
                .queryParam("status", "blah")
                .log().all()
                .get()
                .then()
                .statusCode(400)
                .log().all()
                ;


    }

    /** negative test
     * Find all pets that are at invalid status i.e. not in (available, pending, sold)
     * expected to return 400, when no status, according to swagger (status is mandatory)
     * * !!! This test has failure
     */
    @Test
    public void givenNoStatus_findAllPetDetailsByStatus_thenBadRequest400() {

        given()
                .basePath(PETSTORE_URL + objectName)
                .log().all()
                .get()
                .then()
                .statusCode(400)
                .log().all()
                ;


    }
}
