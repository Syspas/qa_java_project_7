import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("API Тестирование")
@Feature("Получение количества заказов для курьера")
@Story("Получение количества заказов для курьера")
public class Test4Couriers extends BaseAPITest {

    private static final Logger logger = Logger.getLogger(Test4Couriers.class);

    /**
     * Метод для получения количества заказов для указанного курьера.
     *
     * @param courierId Идентификатор курьера.
     * @return Ответ сервера.
     */
    @Description("Класс для тестирования API эндпоинта Курьеры")
    @Step("Получение количества заказов для курьера")
    public Response getOrdersCountForCourier(int courierId) {
        logger.info("Отправка запроса на получение количества заказов для курьера с ID: " + courierId);
        return given()
                .baseUri(baseURI)
                .when()
                .get("/api/v1/courier/" + courierId + "/ordersCount")
                .then()
                .extract()
                .response();
    }

    /**
     * Метод для проверки статус-кода и содержания ответа.
     *
     * @param response             Ответ сервера.
     * @param expectedStatusCode   Ожидаемый статус-код.
     * @param expectedId           Ожидаемый идентификатор курьера.
     * @param expectedOrdersCount  Ожидаемое количество заказов.
     */
    @Step("Проверка статус-кода и содержания ответа")
    public void assertResponseData(Response response, int expectedStatusCode, String expectedId, String expectedOrdersCount) {
        logger.info("Проверка ответа: ожидаемый статус-код = " + expectedStatusCode + ", фактический статус-код = " + response.getStatusCode());
        assertEquals(expectedStatusCode, response.getStatusCode());
        assertEquals(expectedId, response.jsonPath().getString("id"));
        assertEquals(expectedOrdersCount, response.jsonPath().getString("ordersCount"));
        logger.info("Проверка данных прошла успешно.");
    }

    @Test
    @Description("Тест на получение количества заказов для существующего курьера")
    @DisplayName("Проверка количества заказов для курьера с ID 1")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation Couriers - Получить количество заказов курьера", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Couriers-GetOrdersCountByCourierId")
    @Issue("T-712345")
    @TmsLink("TMS-101")
    @Step("Проверка количества заказов для курьера с ID 1")
    public void testOrdersCountExistingCourier() {
        int courierId = 1;
        Response response = getOrdersCountForCourier(courierId);
        assertResponseData(response, 200, "123456", "100500");
    }

    @Test
    @Description("Тест на получение количества заказов без указания ID курьера")
    @DisplayName("Проверка количества заказов при отсутствии ID курьера")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation Couriers - Получить количество заказов курьера", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Couriers-GetOrdersCountByCourierId")
    @Issue("T-112346")
    @TmsLink("TMS-102")
    @Step("Проверка количества заказов при отсутствии ID курьера")
    public void testOrdersCountMissingId() {
        logger.warn("Тестирование случая с отсутствующим ID курьера.");
        Response response = getOrdersCountForCourier(0); // Изменено на 0 или на другое значение по умолчанию
        assertResponseData(response, 400, "Недостаточно данных для поиска", null);
    }

    @Test
    @Description("Тест на получение количества заказов для несуществующего курьера")
    @DisplayName("Проверка количества заказов для несуществующего курьера")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "API Documentation Couriers - Получить количество заказов курьера", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Couriers-GetOrdersCountByCourierId")
    @Issue("T-12347")
    @TmsLink("TMS-103")
    @Step("Проверка количества заказов для несуществующего курьера")
    public void testOrdersCountNonExistingCourier() {
        int courierId = 999;
        logger.warn("Тестирование случая с несуществующим курьером с ID: " + courierId);
        Response response = getOrdersCountForCourier(courierId);
        assertResponseData(response, 404, "Курьер не найден", null);
    }
}
