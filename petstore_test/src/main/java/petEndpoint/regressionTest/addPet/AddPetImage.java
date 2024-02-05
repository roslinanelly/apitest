package petEndpoint.regressionTest.addPet;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import petEndpoint.regressionTest.BaseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class AddPetImage extends BaseTest {

    private static final String petId = "2222222222" ;
    private static final String imageFileName = "my_pet1.png";

    /** positive test
     * Upload pet photo to existing pet
     * AdditionalMetadata and File are provided
     * expected to return 200
     */
    @Test
    public void givenValidPetId_uploadPetPhotoWithAllDetails_thenSuccess200_withResponseBodyMatched() throws IOException, JSONException {

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

        // upload pet photo and additional info for the created pet above

        String metadataInfo = "some info about the pet";
        String actualResponse =
                given()
                        .basePath(PETSTORE_URL + "/" + petId + "/" + "uploadImage")
                        .contentType("multipart/form-data")
                        .multiPart("additionalMetadata", metadataInfo)
                        .multiPart("file", new File(resourcePath + imageFileName))
                        .log().all()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract().asString()

                ;

        String expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Response_PetPhotoUploaded_200.json")))
                .replace("<metadataInfo>", metadataInfo)
                .replace("<imageFileName>", imageFileName)
                ;

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

    }

    /** positive test
     * Upload pet photo to existing pet
     * Only File is provided
     * expected to return 200
     */
    @Test
    public void givenValidPetId_uploadPetPhotoWithoutMetadata_thenSuccess200_withResponseBodyMatched() throws IOException, JSONException {

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

        // upload pet photo and additional info for the created pet above

        String actualResponse =
                given()
                        .basePath(PETSTORE_URL + "/" + petId + "/" + "uploadImage")
                        .contentType("multipart/form-data")
                        .multiPart("file", new File(resourcePath + imageFileName))
                        .log().all()
                        .post()
                        .then()
                        .statusCode(200)
                        .extract().asString()

                ;

        String expectedResponse = new String(Files.readAllBytes(Paths.get(resourcePath + "Response_PetPhotoUploaded_200.json")))
                .replace("<metadataInfo>", "null")
                .replace("<imageFileName>", imageFileName)
                ;

        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.NON_EXTENSIBLE);

    }

    /** negative test
     * Upload pet photo with metadata info to non-existing pet
     * expected to return 400 (mandatory fields missing) or maybe 404
     * !!! currently returning 500 - test failure
     */
    @Test
    public void givenInvalidPetId_uploadPetPhoto_thenError4XX() {

        // delete the petId no matter what the response status it
        given()
            .basePath(PETSTORE_URL + "/" + petId)
            .log().all()
            .delete()
        ;

        // upload pet photo and additional info for the deleted pet above

        String metadataInfo = "some info about the pet";

        given()
                .basePath(PETSTORE_URL + "/" + petId + "/" + "uploadImage")
                .contentType("multipart/form-data")
                .multiPart("additionalMetadata", metadataInfo)
                .multiPart("file", new File(resourcePath + imageFileName))
                .log().all()
                .post()
                .then()
                .statusCode(400)
        ;

    }

    /** negative test
     * Missing pet photo file to existing pet when uploading photo
     * expected to return 400, but according to swagger photo is optional (?)
     */
    @Test
    public void givenValidPetId_uploadPetPhotoWithoutImageFile_thenError400() throws IOException {

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

        // Missing pet photo for the created pet above

        String metadataInfo = "some info about the pet";

        given()
                .basePath(PETSTORE_URL + "/" + petId + "/" + "uploadImage")
                .contentType("multipart/form-data")
                .log().all()
                .post()
                .then()
                .statusCode(400)
                .log().all()

        ;


    }

}
