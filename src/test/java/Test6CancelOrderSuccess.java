import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API для отмены заказа.
 * <p>
 * Этот класс содержит тесты, которые проверяют возможность успешной отмены заказа,
 * а также обработку ошибок, таких как отсутствие номера заказа, попытка отмены
 * несуществующего заказа и отмена заказа, который уже в работе.
 * </p>
 */
@Epic("API Тестирование")
@Feature("Отмена заказов")
@Story("Заказ и управление заказами")
public class Test6CancelOrderSuccess extends BaseAPITest {

    /**
     * Тест для успешной отмены заказа.
     * <p>
     * Этот тест отправляет запрос на отмену заказа с действительным трек-номером.
     * Ожидается, что ответ будет содержать статус 200 и поле "ok" должно быть true.
     * </p>
     */
    @Test
    @DisplayName("Успешная отмена заказа")
    @Description("Проверяет успешную отмену заказа по действительному трек-номеру.")
    @Severity(SeverityLevel.BLOCKER)
    @Link("https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CancelOrder")
    @Issue("TICKET-123")
    @TmsLink("TEST-456")
    @Step("Проверяет успешную отмену заказа по действительному трек-номеру.")
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
     * <p>
     * Этот тест проверяет, что если не указан трек-номер,
     * сервер возвращает ошибку 400 с соответствующим сообщением.
     * </p>
     */
    @Test
    @DisplayName("Отмена заказа без трек-номера")
    @Description("Проверяет ответ сервера при отсутствии номера заказа.")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CancelOrder")
    @Issue("TICKET-124")
    @TmsLink("TEST-457")
    @Step("Проверяет ответ сервера при отсутствии номера заказа.")
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
     * <p>
     * Этот тест проверяет, что если указан трек-номер, который не существует,
     * сервер возвращает ошибку 404 с соответствующим сообщением.
     * </p>
     */
    @Test
    @DisplayName("Отмена несуществующего заказа")
    @Description("Проверяет ответ сервера при попытке отменить несуществующий заказ.")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CancelOrder")
    @Issue("TICKET-125")
    @TmsLink("TEST-458")
    @Step("Проверяет ответ сервера при попытке отменить несуществующий заказ.")
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
     * <p>
     * Этот тест проверяет, что если заказ уже в работе,
     * сервер возвращает ошибку 409 с соответствующим сообщением.
     * </p>
     */
    @Test
    @DisplayName("Отмена заказа, который уже в работе")
    @Description("Проверяет ответ сервера при попытке отменить заказ, который уже в работе.")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CancelOrder")
    @Issue("TICKET-126")
    @TmsLink("TEST-459")
    @Step("Проверяет ответ сервера при попытке отменить заказ, который уже в работе.")
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
