import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

/**
 * Orders - Получить заказ по его номеру
 * GET
 * /api/v1/orders/track
 * Параметр
 * Название: t
 * Тип: number
 * Описание: Трекинговый номер заказа
 *
 * Запрос: /v1/orders/track?t=123456
 *
 * https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrderByTrackNumber
 */


public class Test8Orders extends BaseAPITest {

    @Test
    public void testGetOrderByNumber() {
        given()
                .baseUri(baseURI)
                .param("t", 123456)
                .when()
                .get("/api/v1/orders/track")
                .then()
                .statusCode(200)
                .body("order.id", equalTo(2))
                .body("order.firstName", equalTo("Naruto"))
                .body("order.lastName", equalTo("Uzumaki"))
                .body("order.address", equalTo("Kanoha, 142 apt."))
                .body("order.metroStation", equalTo("1"))
                .body("order.phone", equalTo("+7 800 355 35 35"))
                .body("order.rentTime", equalTo(5))
                .body("order.deliveryDate", equalTo("2020-06-06T00:00:00.000Z"))
                .body("order.track", equalTo(521394))
                .body("order.status", equalTo(1))
                .body("order.color[0]", equalTo("BLACK"))
                .body("order.comment", equalTo("Saske, come back to Kanoha"))
                .body("order.cancelled", equalTo(false))
                .body("order.finished", equalTo(false))
                .body("order.inDelivery", equalTo(false))
                .body("order.courierFirstName", equalTo("Kaneki"))
                .body("order.createdAt", equalTo("2020-06-08T14:40:28.219Z"))
                .body("order.updatedAt", equalTo("2020-06-08T14:40:28.219Z"));
    }

    @Test
    public void testGetOrder_ValidTrackNumber_ReturnsOrderInfo() {
        // Задаем трекинговый номер
        String trackNumber = "123456";

        // Выполняем GET запрос
        Response response = given()
                .baseUri(baseURI)
                .queryParam("t", trackNumber)
                .when()
                .get("/orders/track")
                .then()
                .extract()
                .response();

        // Проверяем успешный ли запрос
        response.then().statusCode(200);

        // Проверяем содержание ответа
        response.then().body("message", nullValue()); // Проверка на отсутствие сообщения об ошибке
        response.then().body("orderNumber", equalTo(trackNumber)); // Проверка на соответствие номера заказа
        // Другие проверки на содержание данных заказа, если необходимо
    }

    @Test
    public void testGetOrder_InvalidTrackNumber_ReturnsNotFound() {
        // Задаем несуществующий трекинговый номер
        String trackNumber = "999999";

        // Выполняем GET запрос
        Response response = given()
                .baseUri(baseURI)
                .queryParam("t", trackNumber)
                .when()
                .get("/orders/track")
                .then()
                .extract()
                .response();

        // Проверяем, что вернулся статус 404 Not Found
        response.then().statusCode(404);

        // Проверяем содержание ответа
        response.then().body("message", equalTo("Заказ не найден"));
    }
}
