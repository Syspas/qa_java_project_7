import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test12Utils extends BaseAPITest {
    /**
     * Проверка успешного ответа на запрос ping сервера
     */
    @Test
    public void testPingServerSuccess() {


        Response response = given()
                .baseUri(baseURI)
                .when()
                .get("/api/v1/ping")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());
        assertEquals("pong", response.getBody().asString());
    }
}
