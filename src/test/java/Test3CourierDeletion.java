import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Класс для тестирования удаления курьера.
 * Методы для отправки запросов DELETE по адресу /api/v1/courier/:id
 * Параметр id - номер курьера, хранится в поле id таблицы Couriers
 *
 * Примеры запросов:
 * - Успешное удаление курьера:
 *   Запрос: DELETE /api/v1/courier/3
 *   Ответ: HTTP/1.1 200 OK
 *           "ok": true
 *
 * - Запрос без указания id:
 *   Запрос: DELETE /api/v1/courier/
 *   Ответ: HTTP/1.1 400 Bad Request
 *           "message": "Недостаточно данных для удаления курьера"
 *
 * - Запрос с несуществующим id:
 *   Запрос: DELETE /api/v1/courier/999
 *   Ответ: HTTP/1.1 404 Not Found
 *           "message": "Курьера с таким id нет"
 */

@Epic("API Тестирование")
@Feature("Удаление курьера")
@Story("Удаление курьера")
public class Test3CourierDeletion extends BaseAPITest {

    @Test
    @DisplayName("Успешное удаление курьера")
    @Description("Тест проверяет успешное удаление курьера по его ID")
    @Severity(SeverityLevel.CRITICAL)//Важность теста критическая
    @Story("Успешное удаление курьера")
    @Link(name = "API Documentation Courier - Удаление курьера", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-DeleteCourier")
    @Issue("BUG-123") // ошибка
    @TmsLink("TEST-456") // ссылка на задачу допустим которая описывает работу теста
    @Step("Успешное удаление курьера")
    public void testSuccessfulCourierDeletion() {
        given()
                .baseUri(baseURI)
                .delete("/api/v1/courier/3")
                .then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Отсутствие ID при удалении курьера")
    @Description("Тест проверяет ответ API при попытке удаления курьера без указания ID")
    @Link(name = "API Documentation Courier - Удаление курьера", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-DeleteCourier")
    @Severity(SeverityLevel.NORMAL)
    @Story("Отсутствие ID при удалении курьера")
    @Step("Отсутствие ID при удалении курьера")
    public void testMissingIdOnCourierDeletion() {
        given()
                .baseUri(baseURI)
                .delete("/api/v1/courier/")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для удаления курьера"));

    }

    @Test
    @DisplayName("Удаление несуществующего курьера")
    @Link(name = "API Documentation Courier - Удаление курьера", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-DeleteCourier")
    @Description("Тест проверяет ответ API при попытке удаления курьера с несуществующим ID")
    @Severity(SeverityLevel.MINOR)
    @Story("Удаление несуществующего курьера")
    @Step("Удаление несуществующего курьера")
    public void testNonexistentIdCourierDeletion() {
        given()
                .baseUri(baseURI)
                .delete("/api/v1/courier/999")
                .then()
                .statusCode(404)
                .body("message", equalTo("Курьера с таким id нет"));
    }
}
