import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Проверка API")
@Feature("Пингующий запрос")
@Story("Проверка успешности запроса ping")
public class Test12Utils extends BaseAPITest {

    /**
     * Проверка успешного ответа на запрос ping сервера.
     *
     * Этот тест проверяет, что сервер отвечает на GET-запрос по адресу
     * /api/v1/ping и возвращает статус 200 и текст "pong".
     */
    @DisplayName("Проверка успешного ответа на пинг")
    @Description("Данный тест проверяет успешный ответ сервера на запрос ping.")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://example.com/api_documentation/ping") // Замените на реальную ссылку
    @Issue("12346") // Замените на существующий номер задачи
    @TmsLink("TC-5679") // Замените на существующий номер тест-кейса
    @Step("Отправка запроса на пинг сервера")
    @Test
    public void testPingServerSuccess() {

        // Отправка GET-запроса и получение ответа
        Response response = given()
                .baseUri(baseURI)
                .when()
                .get("/api/v1/ping")
                .then()
                .extract()
                .response();

        // Проверка кода статуса и тела ответа
        assertEquals(200, response.getStatusCode());
        assertEquals("pong", response.getBody().asString());
    }
}
