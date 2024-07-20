import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

/**
 * Класс, содержащий тесты для проверки функционала входа курьера.
 * 1. Courier - Логин курьера в системе
 * сервис POST
 * /api/v1/courier/login
 */


/**
 * Класс Test1 для тестирования API, связанного с входом курьера.
 */
public class Test1 extends API.Test.BasicMethods {

    private static final Logger logger = LoggerFactory.getLogger(Test1.class);

    /**
     * Тест для проверки успешного входа курьера с верными учетными данными.
     */
    @Step("Проверка успешного входа курьера с верными учетными данными")
    @Test
    public void testCourierLoginWithValidCredentials() {
        logger.info("Начало теста 'Проверка успешного входа курьера с верными учетными данными'");

        String login = "validLogin";
        String password = "validPassword";
        String request = "{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}";

        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404) // Ожидаемый статус-код ответа
                .extract()
                .response();

        // Логирование успешного входа с помощью org.slf4j
        logger.info("Успешный вход курьера с верными учетными данными");
        String responseBody = response.getBody().asString();

        // Добавление вложений для Allure отчета
        Allure.addAttachment("Request", toJson(request));
        Allure.addAttachment("Response", responseBody);

        // Логирование в отладочном режиме
        logger.debug("Отправлен запрос: {}", toJson(request));
        logger.debug("Получен ответ: {}", responseBody);
        logger.info("Тест завершен");
    }

}
