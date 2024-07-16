import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static io.restassured.RestAssured.*;

/**
 * Класс тестирования API для отмены заказа.
 * https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CancelOrder
 * /api/v1/orders/cancel
 */

public class Test_6_CancelOrderSuccess extends BaseAPITest  {

    /**
     * Тест для успешной отмены заказа.
     */
    @Test
    public void testCancelOrderSuccess() {
        String trackNumber = "123456";
        Response response = given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON)
                .body("{\"track\": \"" + trackNumber + "\"}")
                .when()
                .put("/api/v1/orders/cancel")
                .then()
                .statusCode(200)
                .extract()
                .response();

        boolean ok = response.jsonPath().getBoolean("ok");
        Assertions.assertTrue(ok, "Ожидалось, что 'ok' будет true в ответе");
    }

    /**
     * Тест для отмены заказа без указания номера заказа.
     */
    @Test
    public void testCancelOrderMissingTrackNumber() {
        Response response = given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/v1/orders/cancel")
                .then()
                .statusCode(400)
                .extract()
                .response();

        String message = response.jsonPath().getString("message");
        Assertions.assertEquals(message, "Недостаточно данных для поиска", "Ожидаемое сообщение об ошибке не найдено");
    }

    /**
     * Тест для отмены несуществующего заказа.
     */
    @Test
    public void testCancelOrderOrderNotFound() {
        String nonExistingTrackNumber = "999999";
        Response response = given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON)
                .body("{\"track\": \"" + nonExistingTrackNumber + "\"}")
                .when()
                .put("/api/v1/orders/cancel")
                .then()
                .statusCode(404)
                .extract()
                .response();

        String message = response.jsonPath().getString("message");
        Assertions.assertEquals(message, "Заказ не найден", "Ожидаемое сообщение об ошибке не найдено");
    }

    /**
     * Тест для отмены заказа, который уже находится в работе.
     */
    @Test
    public void testCancelOrderOrderAlreadyInProgress() {
        String inProgressTrackNumber = "789012";
        Response response = given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON)
                .body("{\"track\": \"" + inProgressTrackNumber + "\"}")
                .when()
                .put("/api/v1/orders/cancel")
                .then()
                .statusCode(409)
                .extract()
                .response();

        String message = response.jsonPath().getString("message");
        Assertions.assertEquals(message, "Этот заказ уже в работе", "Ожидаемое сообщение об ошибке не найдено");
    }
}
