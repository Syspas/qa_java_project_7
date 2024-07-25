import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static groovy.json.JsonOutput.toJson;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Класс, содержащий тесты для проверки функционала входа курьера.
 * Endpoint: POST /api/v1/courier/login
 */
@Epic("API Тестирование")
@Feature("Выполнение входа на сайт")
@Story("Вход курьера на сайт")
public class Test1CourierLogin extends BaseAPITest {

    static final Logger logger = LoggerFactory.getLogger(Test1CourierLogin.class);

    /**
     * Тест для проверки успешного входа курьера с верными учетными данными.
     */
    @DisplayName("Логин курьера c корректны данными")
    @Description("Проверка успешного входа курьера с верными учетными данными")
    @Severity(SeverityLevel.CRITICAL)//Уровень критичности
    @Link(name = "API Documentation Courier - Логин курьера в системе", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-Login")
    @Step("Проверка успешного входа курьера с верными учетными данными")
    @Issue("BUG-123") // ошибка
    @TmsLink("TEST-456") // ссылка на задачу допустим которая описывает работу теста
    @Test
    public void testCourierLoginWithValidCredentials() {
        logger.info("Начало теста 'Проверка успешного входа курьера с верными учетными данными'");

        // Учетные данные для входа
        String login = "validLogin";
        String password = "validPassword";
        String request = "{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}";

        // Отправка POST запроса на сервер
        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200) // Ожидаемый статус-код ответа (здесь ошибка, должно быть 200)
                // Извлечение ответа
                .extract()
                .response();

        // Логирование успешного входа с использованием SLF4J
        logger.info("Успешный вход курьера с верными учетными данными");
        String responseBody = response.getBody().asString();

        // Добавление вложений для Allure отчета
        Allure.addAttachment("Запрос", toJson(request));
        Allure.addAttachment("Ответ", responseBody);

        // Логирование в отладочном режиме
        logger.debug("Отправлен запрос: {}", toJson(request));
        logger.debug("Получен ответ: {}", responseBody);
        logger.info("Тест завершен");
    }


    /**
     * Проверка ответа API при отсутствии данных для входа
     */
    @DisplayName("Логин курьера с недостаточными данными")
    @Description("Проверка ответа API при отсутствии данных для входа")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation Courier - Логин курьера в системе", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-Login")
    @Issue("BUG-1234")
    @TmsLink("TEST-4567")
    @Test
    public void testCourierLoginWithMissingData() {
        // Учетные данные для входа
        String request = "{}";
        // Подготовка тестовых данных и отправка запроса
        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"))
                .extract()
                .response();

        // Логирование ответа при отсутствии данных для входа
        logger.error("Ответ API при отсутствии данных для входа");
        String responseBody = response.getBody().asString();

        // Добавление вложений для Allure отчета
        Allure.addAttachment("Запрос", toJson(request));
        Allure.addAttachment("Ответ", responseBody);

        // Логирование в отладочном режиме
        logger.debug("Отправлен запрос: {}", toJson(request));
        logger.debug("Получен ответ: {}", responseBody);
        logger.info("Тест завершен");

    }

    /**
    * Проверка ответа API при использовании несуществующих учетных данных
    */
    @DisplayName("Логин курьера с использованием несуществующих учетных данных")
    @Description("Проверка ответа API при с использованием несуществующих учетных данных")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "API Documentation Courier - Логин курьера в системе", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-Login")
    @Issue("BUG-1234")
    @TmsLink("TEST-4567")
    @Step("Проверка ответа API при использовании несуществующих учетных данных")
    @Test
    public void testCourierLoginWithNonExistingCredentials() {
            String login = "nonExistingLogin";
            String password = "nonExistingPassword";
            String requestBody = "{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}";

            // Логирование запроса
            logger.debug("Отправлен запрос: {}", requestBody);

            Response response = given()
                    .baseUri(baseURI)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/api/v1/courier/login")
                    .then()
                    .statusCode(400)
                    .body("message", equalTo("Недостаточно данных для входа"))
                    .extract()
                    .response();

            // Логирование ответа при отсутствии данных для входа
            logger.error("Проверка ответа API при использовании несуществующих учетных данных");
            String responseBody = response.getBody().asString();

            // Добавление вложений для Allure отчета
            Allure.addAttachment("Запрос", requestBody);
            Allure.addAttachment("Ответ", responseBody);

            // Логирование в отладочном режиме
            logger.debug("Получен ответ: {}", responseBody);
            logger.info("Тест завершен");
        }
    }



