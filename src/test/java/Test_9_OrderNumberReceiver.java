import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;



/**
 * Класс для тестирования API получения заказов по номеру трекинга.
* Orders - Получить заказ по его номеру
*
* GET /api/v1/orders/track
* Параметр:
*   t - номер трекинга заказа (number)
*   https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrderByTrackNumber
*/

public class Test_9_OrderNumberReceiver extends BaseAPITest  {

    /**
     * Тест для получения заказа по номеру трекинга.
     */
    @Test
    public void testGetOrderByTrackingNumber() {
        String trackingNumber = "123456";

        Response response = given()
                .baseUri(baseURI)
                .queryParam("t", trackingNumber)
                .when()
                .get("/api/v1/orders/track")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("order.id", equalTo(2))
                .body("order.firstName", equalTo("Naruto"))
                .body("order.lastName", equalTo("Uzumaki"))
                .body("order.address", equalTo("Kanoha, 142 apt."))
                .body("order.metroStation", equalTo("1"))
                .body("order.phone", equalTo("+7 800 355 35 35"))
                .body("order.deliveryDate", equalTo("2020-06-06T00:00:00.000Z"))
                .body("order.track", equalTo(521394))
                .body("order.status", equalTo(1))
                .body("order.color", hasItem("BLACK"))  // проверка, что в массиве есть "BLACK"
                .body("order.comment", equalTo("Saske, come back to Kanoha"))
                .body("order.cancelled", equalTo(false))
                .body("order.finished", equalTo(false))
                .body("order.inDelivery", equalTo(false))
                .body("order.courierFirstName", equalTo("Kaneki"))
                .body("order.createdAt", equalTo("2020-06-08T14:40:28.219Z"))
                .body("order.updatedAt", equalTo("2020-06-08T14:40:28.219Z"))
                .extract().response();

        // Дополнительные проверки и утверждения можно добавить сюда
    }

    @Test
    public void testGetOrderDetailsByTrackingNumber() {
        String trackingNumber = "1234567890"; // Пример существующего трекингового номера

        given()
                .queryParam("t", trackingNumber)
                .when()
                .get("/api/v1/orders/track")
                .then()
                .statusCode(200)
                .body("trackingNumber", equalTo(trackingNumber)); // Проверка, что трекинговый номер совпадает
    }

    @Test
    public void testBadRequestWhenNoTrackingNumberProvided() {
        given()
                .when()
                .get("/api/v1/orders/track")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    public void testOrderNotFound() {
        String nonExistingTrackingNumber = "9999999999"; // Пример несуществующего трекингового номера

        given()
                .queryParam("t", nonExistingTrackingNumber)
                .when()
                .get("/api/v1/orders/track")
                .then()
                .statusCode(404)
                .body("message", equalTo("Заказ не найден"));
    }


}
