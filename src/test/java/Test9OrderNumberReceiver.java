import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


/**
 * @Epic - Эпик, к которому относится данный класс тестов.
 * @Feature - Функциональная возможность, которую проверяют эти тесты.
 * @Story - Конкретная история или сценарий, в рамках которого выполняются тесты.
 *
 * Класс для тестирования API получения заказов по номеру трекинга.
 * Orders - Получить заказ по его номеру
 *
 * GET /api/v1/orders/track
 * Параметр:
 *   t - номер трекинга заказа (number)
 *   @Link https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrderByTrackNumber
 */

@Epic ("API Тестирование")
@Feature ("тестирования API получения заказов по номеру трекинга")
@Story ("Тест для получения заказа ")
public class Test9OrderNumberReceiver extends BaseAPITest {

    /**
     * @DisplayName - указывает на наглядное название теста.
     * @Description - добавляет описание теста.
     * @Severity - задает уровень важности теста.
     * @Link - добавляет ссылку на документацию API.
     * @Issue - связывает тест с задачей в системе отслеживания ошибок.
     * @TmsLink - связывает тест с тест-кейсом в системе управления тестами.
     * @Step - добавляет шаг теста.
     */
    @DisplayName("Тест для получения заказа по номеру трекинга.")
    @Description("В этом тесте проверяется корректность получения заказа по трекинговому номеру.")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrderByTrackNumber")
    @Issue("BUG-1234")
    @TmsLink("TC-789")
    @Step("В этом тесте проверяется корректность получения заказа по трекинговому номеру.")
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

    @DisplayName ("Тест для получения деталей заказа по номеру трекинга.")
    @Description ("В этом тесте проверяется корректность получения деталей заказа по трекинговому номеру.")
    @Severity(SeverityLevel.NORMAL)
    @Step("В этом тесте проверяется корректность получения деталей заказа по трекинговому номеру.")
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

    @DisplayName ("Тест на некорректный запрос при отсутствии номера трекинга.")
    @Description ("В этом тесте проверяется обработка ситуации, когда номер трекинга не указан.")
    @Severity(SeverityLevel.MINOR)
    @Step("В этом тесте проверяется обработка ситуации, когда номер трекинга не указан.")
    @Test
    public void testBadRequestWhenNoTrackingNumberProvided() {
        given()
                .when()
                .get("/api/v1/orders/track")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @DisplayName ("Тест на отсутствие заказа с указанным трекинговым номером.")
    @Description ("В этом тесте проверяется обработка ситуации, когда заказ с указанным трекинговым номером не найден.")
    @Severity(SeverityLevel.MINOR)
    @Step("Тест на отсутствие заказа с указанным трекинговым номером.")
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