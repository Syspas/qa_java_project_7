import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("API Тестирование")
@Feature("Управление заказами")
@Story("Управление заказами")
public class Test5FinishOrder extends BaseAPITest {

    /**
     * Тест на успешное завершение заказа с действительным ID заказа.
     */
    @Test
    @DisplayName("Успешное завершение заказа")
    @Description("Проверяет успешное завершение заказа по действительному ID.")
    @Severity(SeverityLevel.BLOCKER)
    @Link(name = "API Documentation Orders - Завершить заказ", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-FinishOrder")
    @Issue("TICKET-123")
    @TmsLink("TEST-456")
    @Story("Завершение существующего заказа")
    @Step("Проверяет успешное завершение заказа по действительному ID.")
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

    /**
     * Тест на завершение заказа без указания ID заказа.
     */
    @Test
    @DisplayName("Завершение заказа без ID")
    @Description("Проверяет ответ сервера при попытке завершить заказ без ID.")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation Orders - Завершить заказ", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-FinishOrder")
    @Issue("TICKET-123")
    @TmsLink("TEST-456")
    @Story("Проверка обработки отсутствующего ID")
    @Step("Проверяет ответ сервера при попытке завершить заказ без ID.")
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

    /**
     * Тест на завершение заказа с несуществующим ID заказа.
     */
    @Test
    @DisplayName("Завершение заказа с несуществующим ID")
    @Description("Проверяет ответ сервера, если ID заказа не существует.")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation Orders - Завершить заказ", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-FinishOrder")
    @Issue("TICKET-123")
    @TmsLink("TEST-456")
    @Story("Проверка обработки несуществующего ID заказа")
    @Step("Проверяет ответ сервера, если ID заказа не существует.")
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

    /**
     * Тест на завершение заказа с несуществующим ID курьера.
     */
    @Test
    @DisplayName("Завершение заказа с несуществующим ID курьера")
    @Description("Проверяет ответ сервера, если ID курьера не существует.")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation Orders - Завершить заказ", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-FinishOrder")
    @Issue("TICKET-123")
    @TmsLink("TEST-456")
    @Story("Проверка обработки несуществующего ID курьера")
    @Step("Проверяет ответ сервера, если ID курьера не существует.")
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

    /**
     * Тест на конфликт при попытке завершить заказ, который нельзя завершить.
     */
    @Test
    @DisplayName("Конфликт при завершении заказа")
    @Description("Проверяет ответ сервера, если заказ нельзя завершить.")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation Orders - Завершить заказ", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-FinishOrder")
    @Issue("TICKET-123")
    @TmsLink("TEST-456")
    @Story("Обработка конфликта при завершении заказа")
    @Step("Проверяет ответ сервера, если заказ нельзя завершить.")
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
