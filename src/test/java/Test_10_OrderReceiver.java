import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Метод для принятия заказа.
 *
 * HTTP метод: PUT
 * Путь: /api/v1/orders/accept/:id
 *
 * @param id Номер заказа, хранится в поле id таблицы Orders
 * @param courierId Id курьера, хранится в поле id таблицы Couriers
 *
 * @return Статус-код ответа сервера после выполнения операции.
 */


public class Test_10_OrderReceiver extends BaseAPITest {


    @Test
    public void testOrderAcceptance() {
        // Подготовка данных для запроса
        int orderId = 1;
        int courierId = 213;

        // Выполнение запроса PUT
        Response response = given()
                .pathParam("id", orderId)
                .queryParam("courierId", courierId)
                .when()
                .baseUri(baseURI)
                .put("/api/v1/orders/accept/{id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Проверка успешного ответа
        response.then()
                .body("ok", equalTo(true));
    }

    @Test
    public void testAcceptOrder() {
        // Ожидаемый статус код 200 OK
        Response response = given()
                .baseUri(baseURI)
                .param("courierId", 213)
                .put("/api/v1/orders/accept/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        response.then()
                .body("ok", equalTo(true));
    }

    @Test
    public void testBadRequestWithoutNumber() {
        // Ожидаемый статус код 400 Bad Request
        given()
                .baseUri(baseURI)
                .put("/api/v1/orders/accept/1")
                .then()
                .statusCode(400);
    }

    @Test
    public void testConflictWithoutIds() {
        // Ожидаемый статус код 400 Conflict
        given()
                .baseUri(baseURI)
                .put("/api/v1/orders/accept/1")
                .then()
                .statusCode(400);
    }

    @Test
    public void testOrderNotFound() {
        // Ожидаемый статус код 404 Not Found
        given()
                .baseUri(baseURI)
                .put("/api/v1/orders/accept/999?courierId=213")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCourierNotFound() {
        // Ожидаемый статус код 404 Not Found
        given()
                .baseUri(baseURI)
                .put("/api/v1/orders/accept/1?courierId=999")
                .then()
                .statusCode(404);
    }

    @Test
    public void testOrderAlreadyInProgress() {
        // Ожидаемый статус код 409 Conflict
        given()
                .baseUri(baseURI)
                .put("/api/v1/orders/accept/1?courierId=213")
                .then()
                .statusCode(409);
    }
}
