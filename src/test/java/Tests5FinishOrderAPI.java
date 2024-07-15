import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests5FinishOrderAPI extends BaseAPITest {

    @Test
    public void testFinishOrderSuccess() {
        int orderId = 123;
        Response response = given()
                .contentType("application/json")
                .body("{\"id\": " + orderId + "}")
                .when()
                .put(baseURI + orderId)
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());
        assertEquals(true, response.jsonPath().getBoolean("ok"));
    }

    @Test
    public void testFinishOrderWithoutId() {
        Response response = given()
                .contentType("application/json")
                .body("{}")
                .when()
                .put(baseURI)
                .then()
                .extract()
                .response();

        assertEquals(400, response.getStatusCode());
        assertEquals("Недостаточно данных для поиска", response.jsonPath().getString("message"));
    }

    @Test
    public void testFinishOrderNonExistingId() {
        int nonExistingOrderId = 999;
        Response response = given()
                .contentType("application/json")
                .body("{\"id\": " + nonExistingOrderId + "}")
                .when()
                .put(baseURI + nonExistingOrderId)
                .then()
                .extract()
                .response();

        assertEquals(404, response.getStatusCode());
        assertEquals("Заказа с таким id не существует", response.jsonPath().getString("message"));
    }

    @Test
    public void testFinishOrderNonExistingCourier() {
        int nonExistingCourierId = 999;
        Response response = given()
                .contentType("application/json")
                .body("{\"id\": " + nonExistingCourierId + "}")
                .when()
                .put(baseURI + nonExistingCourierId)
                .then()
                .extract()
                .response();

        assertEquals(404, response.getStatusCode());
        assertEquals("Курьера с таким id не существует", response.jsonPath().getString("message"));
    }

    @Test
    public void testFinishOrderConflict() {
        int orderId = 456; // ID заказа, который нельзя завершить
        Response response = given()
                .contentType("application/json")
                .body("{\"id\": " + orderId + "}")
                .when()
                .put(baseURI + orderId)
                .then()
                .extract()
                .response();

        assertEquals(409, response.getStatusCode());
        assertEquals("Этот заказ нельзя завершить", response.jsonPath().getString("message"));
    }
}
