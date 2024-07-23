import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;

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
 * Документация API: https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrderByTrackNumber
 */
@Epic("Заказы")
@Feature("Получение информации о заказах")
@Story("Поиск заказа по трекинговому номеру")
public class Test8Orders extends BaseAPITest {

    @DisplayName("Получить заказ по номеру")
    @Description("Тест проверяет возможность получения информации о заказе по трекинговому номеру.")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrderByTrackNumber")
    @Issue("12345") // Замените на реальный номер задачи
    @TmsLink("TC-7890") // Замените на реальный номер тест-кейса
    @Step("Получение заказа с трекинговым номером 123456")
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

    @DisplayName("Получить информацию о заказе с валидным трекинговым номером")
    @Description("Тест проверяет, что запрос с валидным трекинговым номером возвращает информацию о заказе.")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testGetOrder_ValidTrackNumber_ReturnsOrderInfo() {
        String trackNumber = "123456";

        Response response = given()
                .baseUri(baseURI)
                .queryParam("t", trackNumber)
                .when()
                .get("/orders/track")
                .then()
                .extract()
                .response();

        response.then().statusCode(200);
        response.then().body("message", nullValue());
        response.then().body("orderNumber", equalTo(trackNumber));
    }

    @DisplayName("Получить информацию о несуществующем заказе")
    @Description("Тест проверяет, что запрос с несуществующим трекинговым номером возвращает статус 404.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testGetOrder_InvalidTrackNumber_ReturnsNotFound() {
        String trackNumber = "999999";

        Response response = given()
                .baseUri(baseURI)

                .queryParam("t", trackNumber)
                .when()
                .get("/orders/track")
                .then()
                .extract()
                .response();

        response.then().statusCode(404);
        response.then().body("message", equalTo("Заказ не найден"));
    }
}
