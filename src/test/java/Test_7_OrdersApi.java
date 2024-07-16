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
 */


public class Test_7_OrdersApi extends BaseAPITest {

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

    // Пример теста с фильтрацией по станциям метро
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

    // Пример теста с пагинацией
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
