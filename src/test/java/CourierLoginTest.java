import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Класс, содержащий тесты для проверки функционала входа курьера.
 */
@Story("Вход курьера")
public class CourierLoginTest extends BaseAPITest {

    /**
     * Проверка успешного входа курьера с верными учетными данными
     */
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

        // Добавьте логирование успешного входа здесь
    }

    /**
     * Проверка ответа API при отсутствии данных для входа
     */
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

        // Добавьте логирование ответа при отсутствии данных для входа здесь
    }

    /**
     * Проверка ответа API при использовании несуществующих учетных данных
     */
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

        // Добавьте логирование ответа при использовании несуществующих учетных данных здесь
    }
}
