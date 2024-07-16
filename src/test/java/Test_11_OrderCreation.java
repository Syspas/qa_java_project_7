import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

public class Test_11_OrderCreation extends BaseAPITest {

    /**
     * Тест для проверки успешного создания заказа.
     */
    @Test
    public void testOrderCreationSuccess() {
        String requestBody = "{\n" +
                "    \"firstName\": \"Naruto\",\n" +
                "    \"lastName\": \"Uchiha\",\n" +
                "    \"address\": \"Konoha, 142 apt.\",\n" +
                "    \"metroStation\": 4,\n" +
                "    \"phone\": \"+7 800 355 35 35\",\n" +
                "    \"rentTime\": 5,\n" +
                "    \"deliveryDate\": \"2020-06-06\",\n" +
                "    \"comment\": \"Saske, come back to Konoha\",\n" +
                "    \"color\": [\n" +
                "        \"BLACK\"\n" +
                "    ]\n" +
                "}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/api/v1/orders")
                .then()
                .statusCode(201);
    }
}
