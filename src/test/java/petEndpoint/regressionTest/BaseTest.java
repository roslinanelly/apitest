package petEndpoint.regressionTest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    protected static final String BASE_URI = "https://petstore.swagger.io";
    protected static final String PETSTORE_URL = "v2/pet";
    protected static final String resourcePath = "src/test/resources/";

    @BeforeEach
    public void setup(){
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = PETSTORE_URL;

    }
}
