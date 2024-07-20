import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class Test4CouriersAPI extends BaseAPITest {

    @Description("Класс для тестирования API эндпоинта Couriers")
    @Step("Получение количества заказов курьера")
    public Response getOrdersCountForCourier(int courierId) {
        return given()
                .baseUri(baseURI)
                .when()
                .get("/api/v1/courier/" + courierId + "/ordersCount")
                .then()
                .extract()
                .response();
    }

    @Step("Проверка статус кода и данных в ответе")
    public void assertResponseData(Response response, int expectedStatusCode, String expectedId, String expectedOrdersCount) {
        assertEquals(expectedStatusCode, response.getStatusCode());
        assertEquals(expectedId, response.jsonPath().getString("id"));
        assertEquals(expectedOrdersCount, response.jsonPath().getString("ordersCount"));
    }

    @Description("Тест получения количества заказов существующего курьера")
    @Test
    public void testOrdersCountExistingCourier() {
        int courierId = 1;
        Response response = getOrdersCountForCourier(courierId);
        assertResponseData(response, 200, "123456", "100500");
    }

    @Description("Тест получения количества заказов без указания ID курьера")
    @Test
    public void testOrdersCountMissingId() {
        Response response = getOrdersCountForCourier(0); // Исправлено на 0, или другое значение по умолчанию
        assertResponseData(response, 400, "Недостаточно данных для поиска", null);
    }

    @Description("Тест получения количества заказов для несуществующего курьера")
    @Test
    public void testOrdersCountNonExistingCourier() {
        int courierId = 999;
        Response response = getOrdersCountForCourier(courierId);
        assertResponseData(response, 404, "Курьер не найден", null);
    }
}
