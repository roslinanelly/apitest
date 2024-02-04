package test.java.tests.simpleTests.addPet;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import test.java.tests.BaseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class AddPetImage extends BaseTest {

    private static final String petId = "2222222222" ;

    /** positive test
     * Upload pet photo to existing pet
     * expected to return 200
     */
    @Test
    public void givenValidPetId_uploadPetPhoto_thenSuccess200_withResponseBodyMatched() throws IOException, JSONException {

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
        String imageFileName = "my_pet1.png";
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

}
