package test.java.regressionTest.addPet;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import test.java.BaseTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class AddNewPet extends BaseTest {

    @Test
    public void givenValidMinDetailsRequest_addNewPet_thenSuccess200_withResponseBodyMatched() throws IOException, JSONException {

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

}
