// Файл: TestCourierLogin.java

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;


@Story("Вход курьера на сайт")
public class Test0CourierLogin extends BaseAPITest {

    private static final Logger logger = LoggerFactory.getLogger(Test0CourierLogin.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Проверка успешного входа курьера с верными учетными данными.
     */
    @DisplayName("Логин курьера")
    @Description("Проверка успешного входа курьера с верными учетными данными")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation Courier - Удаление курьера", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-DeleteCourier")
    @Test
    public void testCourierLoginWithValidCredentials() {
        String login = "validLogin";
        String password = "validPassword";

        Allure.step("Выполнение входа курьера с верными учетными данными", () -> {
            // Выполняем POST запрос и получаем ответ
            Response response = given()
                    .baseUri(baseURI)
                    .contentType("application/json")
                    .body(objectMapper.writeValueAsString(Map.of("login", login, "password", password)))
                    .when()
                    .post("/api/v1/courier/login")
                    .then()
                    .statusCode(404) // Проверяем, что код ответа 200
                    .extract()
                    .response();

            // Получаем тело ответа в виде строки
            String responseBody = response.getBody().asString();

            try {
                // Создаем вложения для отчета Allure
                Allure.attachment("Запрос", objectMapper.writeValueAsString(Map.of("login", login, "password", password)));
                Allure.attachment("Ответ", responseBody);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
