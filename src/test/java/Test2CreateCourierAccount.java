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
 *2. Создание курьера
 *
 * Операция POST /api/v1/courier для добавления нового курьера в систему.
 *
 * Параметры:
 * - **login (string):** Логин курьера, который будет использоваться для входа в систему. Значение записывается в поле login таблицы Couriers.
 * - **password (string):** Пароль курьера, который используется для аутентификации. Хэш от значения записывается в поле passwordHash таблицы Couriers.
 * - **firstName (string):** Имя курьера, которое записывается в поле firstName таблицы Couriers.
 */
@Epic("API Тестирование")
@Feature("Создание учетной записи курьера")
@Story("Создание учетной записи курьера")
public class Test2CreateCourierAccount extends BaseAPITest {
    static final Logger logger = LoggerFactory.getLogger(Test2CreateCourierAccount.class);

    @Test
    @DisplayName("Проверка создания учетной записи курьера с заданными параметрами")
    @Description("Проверка создания учетной записи курьера с заданными параметрами")
    @Severity(SeverityLevel.CRITICAL)//Уровень критичности
    @Link(name = "API Documentation Courier - Создание курьера", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-CreateCourier")
    @Issue("BUG-1231") // ошибка
    @TmsLink("TEST-4568") // ссылка на задачу допустим которая описывает работу теста
    @Step("Создание учетной записи курьера с заданными параметрами")
    public void testCreateCourierAccount() {
        //Учетные данные
        String login = "ninja";
        String password = "1234";
        String firstName = "saske";
        // Запрос
        String request = "{\"login\":\"" + login + "\",\"password\":\"" + password + "\",\"firstName\":\"" + firstName + "\"}";

        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true))
                .extract()
                .response();

        //System.out.println("Успешное создание учетной записи: " + response.asString());

        // Логирование ответа при отсутствии данных для входа
        Test1CourierLogin.logger.error("Успешное создание учетной записи: ");
        String responseBody = response.getBody().asString();

        // Добавление вложений для Allure отчета
        Allure.addAttachment("Запрос", toJson(request));
        Allure.addAttachment("Ответ", responseBody);

        // Логирование в отладочном режиме

        logger.debug("Отправлен запрос: {}", toJson(request));
        logger.debug("Получен ответ: {}", responseBody);
        logger.info("Тест завершен");
    }


    @Test
    @DisplayName("Проверка ответа при создании учетной записи без логина или пароля")
    @Description("Проверка ответа при создании учетной записи без логина или пароля")
    @Severity(SeverityLevel.CRITICAL)//Уровень критичности
    @Link(name = "API Documentation Courier - Создание курьера", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-CreateCourier")
    @Issue("BUG-1231") // ошибка
    @TmsLink("TEST-4568") // ссылка на задачу допустим которая описывает работу теста
    @Step("Проверка ответа при создании учетной записи без логина или пароля")
    public void testCreateCourierAccountWithoutLoginOrPassword() {
        //Запрос
        String request = "{\"firstName\":\"saske\"}";

        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .extract()
                .response();

        //System.out.println("Ответ при создании учетной записи без логина или пароля: " + response.asString());

        // Логирование ответа при отсутствии данных для входа
        Test1CourierLogin.logger.error("Ответ при создании учетной записи без логина или пароля: ");
        String responseBody = response.getBody().asString();

        // Добавление вложений для Allure отчета
        Allure.addAttachment("Запрос", toJson(request));
        Allure.addAttachment("Ответ", responseBody);

        // Логирование в отладочном режиме

        logger.debug("Отправлен запрос: {}", toJson(request));
        logger.debug("Получен ответ: {}", responseBody);
        logger.info("Тест завершен");
    }

    @Test
    @DisplayName("Проверка ответа при создании учетной записи с повторяющимся логином")
    @Description("Проверка ответа при создании учетной записи с повторяющимся логином")
    @Severity(SeverityLevel.CRITICAL)//Уровень критичности
    @Link(name = "API Documentation Courier - Создание курьера", type = "swagger", url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-CreateCourier")
    @Issue("BUG-1231") // ошибка
    @TmsLink("TEST-4568") // ссылка на задачу допустим которая описывает работу теста
    @Step("Проверка ответа при создании учетной записи с повторяющимся логином")
    public void testCreateCourierAccountWithDuplicateLogin() {
        //Учетные данные
        String login = "ninja";
        String password = "1234";
        String firstName = "saske";
        // Запрос
        String request = "{\"login\":\"" + login + "\",\"password\":\"" + password + "\",\"firstName\":\"" + firstName + "\"}";

        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"))
                .extract()
                .response();

        //System.out.println("Ответ при создании учетной записи с повторяющимся логином: " + response.asString());


        // Логирование ответа при отсутствии данных для входа
        Test1CourierLogin.logger.error("Ответ при создании учетной записи с повторяющимся логином: ");
        String responseBody = response.getBody().asString();

        // Добавление вложений для Allure отчета
        Allure.addAttachment("Запрос", toJson(request));
        Allure.addAttachment("Ответ", responseBody);

        // Логирование в отладочном режиме

        logger.debug("Отправлен запрос: {}", toJson(request));
        logger.debug("Получен ответ: {}", responseBody);
        logger.info("Тест завершен");



    }
}
