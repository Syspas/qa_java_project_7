import API.Test.Order;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

@Epic("Заказы")
@Feature("Создание заказов")
@Story("Создание заказа для курьера")
public class Test11OrderCreation extends BaseAPITest {

    /**
     * Тест для проверки успешного создания заказа.
     */
    @DisplayName("Создание заказа - успешный сценарий")
    @Description("Данный тест проверяет успешное создание заказа через API.")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation Courier - Orders - Создание заказа", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-AcceptOrder")
    @Issue("12345") // Замените на существующий номер задачи
    @TmsLink("TC-5678") // Замените на существующий номер тест-кейса
    @Step("Отправка запроса на создание заказа")
    @Test
    public void testOrderCreationSuccess() {
        // Создаем объект заказа
        List<String> colors = new ArrayList<>();
        colors.add("BLACK");

        Order order = new Order(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.", 4,
                "+7 800 355 35 35", 5, "2020-06-06",
                "Saske, come back to Konoha", colors);



        // Отправка запроса на создание заказа
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(order) // Используем объект order
                .post("/api/v1/orders")
                .then()
                .statusCode(201);
    }
}
