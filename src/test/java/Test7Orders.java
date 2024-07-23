import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Orders - Получение списка заказов.
 *
 * HTTP метод: GET
 * Путь: /api/v1/orders
 * https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrdersPageByPage
 *
 * Параметры запроса:
 * - courierId (необязательный): Идентификатор курьера. Если указан, возвращает все активные и завершенные заказы этого курьера.
 * - nearestStation (необязательный): Фильтр станций метро в формате JSON, например: { nearestStation: ["1", "2"] }.
 *   Фильтрует вывод по указанным станциям метро.
 * - limit (необязательный): Количество заказов на странице. Максимум: 30. По умолчанию: 30.
 * - page (необязательный): Текущая страница показа заказов. По умолчанию: 0.
 *
 * **Эпик**: Получение заказов
 * **Функция**: Запрос списка заказов
 * **Сюжет**: Проверка различных сценариев получения заказов
 */
@Epic("Получение заказов")
@Feature("Запрос списка заказов")
@Story("Проверка операций с заказами")
public class Test7Orders extends BaseAPITest {

    /**
     * Тест для получения списка заказов без указания идентификатора курьера.
     * <p>
     * Этот тест проверяет, что при отсутствии идентификатора курьера
     * сервер возвращает статус 200 и не пустой список заказов.
     * </p>
     *
*/
    @DisplayName(" Тест: Получение заказов без идентификатора курьера")
    @Description(" Проверяет получение списка заказов без курьера")
    @Severity (SeverityLevel.NORMAL)
    @Link (" https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrdersPageByPage")
    @Issue (" TICKET-127")
    @TmsLink (" TEST-460")
    @Test
    void testGetOrdersWithoutCourierId() {
        given()
                .when()
                .get(baseURI + "/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders.size()", greaterThan(0)); // Проверяем, что список заказов не пустой
        // Добавить другие проверки по необходимости
    }

    /**
     * Тест для получения списка заказов с указанным идентификатором курьера.
     * <p>
     * Этот тест проверяет, что сервер возвращает статус 200 и
     * все заказы принадлежат указанному курьеру.
     * </p>
     *
    */
    @DisplayName("Тест: Получение заказов с идентификатором курьера")
    @Description("Проверяет получение заказов конкретного курьера")
    @Severity(SeverityLevel.NORMAL)
    @Link ("https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrdersPageByPage")
    @Issue("TICKET-128")
    @TmsLink("EST-461")
    @Test
    void testGetOrdersWithCourierId() {
        int courierId = 1;

        given()
                .when()
                .get(baseURI + "/api/v1/orders?courierId=" + courierId)
                .then()
                .statusCode(200)
                .body("orders.size()", greaterThan(0)) // Проверяем, что в ответе есть хотя бы один заказ
                .body("orders.courierId", everyItem(equalTo(courierId))) // Проверяем, что все заказы принадлежат указанному курьеру
                .body("orders", everyItem(hasKey("id"))) // Проверяем, что каждый заказ имеет поле "id"
                .body("orders", everyItem(hasKey("courierId"))) // Проверяем, что каждый заказ имеет поле "courierId"
                .body("orders", everyItem(hasKey("status"))); // Проверяем, что каждый заказ имеет поле "status"
        // Добавить другие проверки по необходимости
    }

/**
 * Тест для получения списка заказов с невалидным идентификатором курьера.
 * <p>
 * Этот тест проверяет, что сервер возвращает статус 404
 * с соответствующим сообщением об ошибке.
 * </p>
 *
**/
    @DisplayName (" Тест: Получение заказов с невалидным идентификатором курьера")
    @Description (" Проверяет обработку запроса с несуществующим курьером")
    @Severity (SeverityLevel.NORMAL)
    @Link("https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrdersPageByPage")
    @Issue("ICKET-129")
    @TmsLink(" TEST-462")
    @Step("Проверяет обработку запроса с несуществующим курьером")
    @Test
    void testGetOrdersWithInvalidCourierId() {
    int invalidCourierId = 999;
    given()
            .when()
            .get(baseURI + "/api/v1/orders?courierId=" + invalidCourierId)
            .then()
            .statusCode(404)
            .body("message", equalTo("Курьер с идентификатором " + invalidCourierId + " не найден"));
}

    /**
     * Тест для получения списка заказов с фильтрацией по станциям метро.
     * <p>
     * Этот тест проверяет, что сервер возвращает статус 200
     * и список заказов включает доступные станции метро.
     * </p>
     */
    @DisplayName (" Тест: Получение заказов с фильтрацией по станциям метро")
    @Description (" Проверяет фильтрацию заказов по указанным станциям")
    @Severity (SeverityLevel.NORMAL)
    @Link("https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrdersPageByPage")
    @Issue("TICKET-130")
    @TmsLink("TEST-463")
    @Step(" Проверяет фильтрацию заказов по указанным станциям")
    @Test
    void testGetOrdersWithNearestStations() {
        String nearestStationsJson = "{\"nearestStation\": [\"1\", \"2\"]}";
        given()
                .contentType("application/json")
                .body(nearestStationsJson)
                .when()
                .get(baseURI + "/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders.size()", greaterThan(0))
                .body("availableStations.name", hasItems("Бульвар Рокоссовского", "Черкизовская"));
    }

    /**
     * Тест для получения списка заказов с пагинацией.
     * <p>
     * Этот тест проверяет, что сервер возвращает статус 200
     * и корректно обрабатывает параметры пагинации.
     * </p>
     *
     **/
    @DisplayName (" Тест: Получение заказов с пагинацией")
    @Description (" Проверяет параметры пагинации при запросе заказов")
    @Severity (SeverityLevel.NORMAL)
    @Link("https://qa-scooter.praktikum-services.ru/docs/#api-Orders-GetOrdersPageByPage")
    @Issue("TICKET-131")
    @TmsLink("TEST-464")
    @Step("Проверяет параметры пагинации при запросе заказов")
    @Test
    void testGetOrdersWithPagination() {
        int limit = 10;
        int page = 1;
        given()
                .queryParam("limit", limit)
                .queryParam("page", page)
                .when()
                .get(baseURI + "/api/v1/orders")
                .then()
                .statusCode(200)
                .body("pageInfo.page", equalTo(page))
                .body("pageInfo.limit", equalTo(limit));
    }
}
