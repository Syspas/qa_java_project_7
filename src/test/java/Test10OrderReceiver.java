import API.Test.OrdersClient;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Класс тестов для проверки функциональности API приёма заказов курьерами.
 * Содержит тестовые методы, которые проверяют различные сценарии взаимодействия
 * с API заказов.
 */
@Epic("Тестирование API заказов")
@Feature("Прием заказов курьерами")
@Story("Прием заказов для курьеров")
public class Test10OrderReceiver extends BaseAPITest {
    private final OrdersClient ordersClient = new OrdersClient();

    /**
     * Тест проверяет, что заказ может быть успешно принят курьером.
     * Уровень важности: HIGH.
     * @DisplayName Указывает на наглядное название теста.
     * @Description Проверяет успешный приём заказа курьером.
     * @Severity Указывает на уровень важности теста.
     * @Link Ссылка на документацию API.
     * @Issue Связывает тест с задачей в системе отслеживания ошибок.
     * @TmsLink Связывает тест с тест-кейсом в системе управления тестами.
     */
    @Test
    @DisplayName("Успешный прием заказа")
    @Description("Проверяет, что заказ может быть успешно принят курьером.")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation Orders - Принять заказ", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-AcceptOrder")
    @Issue("PROJECT-123") // Замените на реальный номер задачи
    @TmsLink("TMS-456") // Замените на реальный номер тест-кейса
    @Step("Проверяет, что заказ может быть успешно принят курьером.")
    public void testOrderAcceptance() {
        int orderId = 1;
        int courierId = 213;

        Response response = ordersClient.acceptOrder(orderId, courierId,baseURI);

        response.then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    /**
     * Тест проверяет код статуса для несуществующего заказа.
     * @DisplayName Указывает на наглядное название теста.
     * @Description Проверяет получение сообщения об ошибке, если заказ не найден.
     * @Severity Указывает на уровень важности теста.
     */
    @Test
    @DisplayName("Проверка кода статуса для заказа, не найденного")
    @Description("Проверяет, что при попытке принять несуществующий заказ возвращается статус 404.")
    @Link(name = "API Documentation Orders - Принять заказ", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-AcceptOrder")
    @Severity(SeverityLevel.NORMAL)
    @Step("Проверяет, что при попытке принять несуществующий заказ возвращается статус 404.")
    public void testOrderNotFound() {
        int orderId = 999;
        int courierId = 213;
        Response response = ordersClient.acceptOrder(orderId, courierId,baseURI); // 999 - Неверный id

        response.then()
                .statusCode(404)
                .body("message", is("Заказ не найден")); // Проверка сообщения об ошибке
    }

    /**
     * Тест проверяет код статуса для несуществующего курьера.
     * @DisplayName Указывает на наглядное название теста.
     * @Description Проверяет получение сообщения об ошибке, если курьер не найден.
     * @Severity Указывает на уровень важности теста.
     */
    @Test
    @DisplayName("Проверка кода статуса для курьера, не найденного")
    @Description("Проверяет, что при попытке принять заказ курьером, который не найден, возвращается статус 404.")
    @Link(name = "API Documentation Orders - Принять заказ", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-AcceptOrder")
    @Step("Проверяет, что при попытке принять несуществующий заказ возвращается статус 404.")
    @Severity(SeverityLevel.NORMAL)
    public void testCourierNotFound() {
        int orderId = 1;
        int courierId = 999;
        Response response = ordersClient.acceptOrder(1, 999,baseURI); // 999 - Неверный id

        response.then()
                .statusCode(404)
                .body("message", is("Курьер не найден")); // Проверка сообщения об ошибке
    }

    /**
     * Тест проверяет код статуса для уже выполняемого заказа.
     * @DisplayName Указывает на наглядное название теста.
     * @Description Проверяет, что возвращается статус 409, если заказ уже в процессе выполнения.
     * @Severity Указывает на уровень важности теста.
     */
    @Test
    @DisplayName("Проверка кода статуса для заказа, который уже в процессе выполнения")
    @Description("Проверяет, что возвращается статус 409, если заказ уже в процессе выполнения.")
    @Link(name = "API Documentation Orders - Принять заказ", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-AcceptOrder")
    @Severity(SeverityLevel.NORMAL)
    @Step("Проверка кода статуса для заказа, который уже в процессе выполнения")
    public void testOrderAlreadyInProgress() {
        int orderId = 1;
        int courierId = 213;
        Response response = ordersClient.acceptOrder(orderId, courierId, baseURI); // Принять заказ, который уже в работе

        response.then()
                .statusCode(409)
                .body("message", is("Заказ уже в процессе выполнения")); // Проверка сообщения об ошибке
    }
}

