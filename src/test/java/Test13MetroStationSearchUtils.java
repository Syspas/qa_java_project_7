import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Поиск метро")
@Feature("Поиск станции по названию")
@Story("Тестирование поиска станции метро по названию")
public class Test13MetroStationSearchUtils {

    /**
     * Тестирование поиска станции метро по названию "Сокол".
     *
     * Этот тест отправляет запрос на поиск станции метро и проверяет,
     * что ответ содержит ожидаемые данные. Проверяются как минимальные,
     * так и дополнительные станции, соответствующие запросу.
     */
    @DisplayName("Поиск станции метро по названию 'Сокол'")
    @Description("Тест проверяет успешность поиска станции метро по названию 'Сокол' и валидирует данные ответа.")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://qa-scooter.praktikum-services.ru/docs/#api-Utils-StationsSearch") // Замените на реальную ссылку
    @Issue("12345") // Замените на существующий номер задачи
    @TmsLink("TC-7890") // Замените на существующий номер тест-кейса
    @Step("Отправка запроса на поиск станции метро по названию 'Сокол'")
    @Test
    public void testSearchMetroStation() {

        // Отправка GET-запроса с параметром для поиска станции метро
        Response response = given()
                .baseUri(baseURI)
                .queryParam("s", "Сокол")
                .when()
                .get("/api/v1/stations/search")
                .then()
                .extract()
                .response();

        // Проверка кода статуса и данных ответа
        assertEquals(200, response.getStatusCode());
        assertEquals("4", response.jsonPath().getString("[0].number"));
        assertEquals("Сокольники", response.jsonPath().getString("[0].name"));
        assertEquals("#D92B2C", response.jsonPath().getString("[0].color"));

        assertEquals("26", response.jsonPath().getString("[1].number"));
        assertEquals("Сокол", response.jsonPath().getString("[1].name"));
        assertEquals("#4DBE52", response.jsonPath().getString("[1].color"));

        assertEquals("215", response.jsonPath().getString("[2].number"));
        assertEquals("Соколиная Гора", response.jsonPath().getString("[2].name"));
        assertEquals("#efadb5", response.jsonPath().getString("[2].color"));
    }
}
