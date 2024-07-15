import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Класс, содержащий тесты для проверки функционала входа курьера.
 * 1. Courier - Логин курьера в системе
 * сервис POST
 * /api/v1/courier/login
 */
@Story("Вход курьера")
public class Tests1CourierLogin extends BaseAPITest {

    private static final Logger logger = LoggerFactory.getLogger(Tests1CourierLogin.class);

    /**
     * Проверка успешного входа курьера с верными учетными данными
     */
    @Step("Проверка успешного входа курьера с верными учетными данными")
    @Test
    public void testCourierLoginWithValidCredentials() {
        String login = "validLogin";
        String password = "validPassword";

        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}")
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        //Логирование успешного входа с помощью org.slf4j
        logger.info("Успешный вход курьера с верными учетными данными");
    }

    /**
     * Проверка ответа API при отсутствии данных для входа
     */
    @Step("Проверка ответа API при отсутствии данных для входа")
    @Test
    public void testCourierLoginWithMissingData() {
        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body("{}")
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"))
                .extract()
                .response();

        //Логирование ответа при отсутствии данных для входа с помощью org.slf4j
        logger.error("Ответ API при отсутствии данных для входа: " + response.body().asString());
    }

    /**
     * Проверка ответа API при использовании несуществующих учетных данных
     */
    @Step("Проверка ответа API при использовании несуществующих учетных данных")
    @Test
    public void testCourierLoginWithNonExistingCredentials() {
        String login = "nonExistingLogin";
        String password = "nonExistingPassword";

        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}")
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"))
                .extract()
                .response();

        // Логирование ответа при использовании несуществующих учетных данных с помощью org.slf4j
        logger.error("Ответ API при использовании несуществующих учетных данных: " + response.body().asString());
    }
}
