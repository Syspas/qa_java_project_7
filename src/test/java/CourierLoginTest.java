import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierLoginTest extends BaseAPITest {

    /**
     * Тест для проверки успешного входа курьера с верными учетными данными.
     */
    @Test
    public void testCourierLoginWithValidCredentials() {
        // Предположим, что у нас есть корректный логин и пароль
        String login = "validLogin";
        String password = "validPassword";

        // Выполнение запроса на вход курьера с использованием заданных учетных данных
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

        // Добавьте проверки успешного входа здесь
    }

    /**
     * Тест для проверки ответа API при отсутствии данных для входа.
     */
    @Test
    public void testCourierLoginWithMissingData() {
        // Выполнение запроса на вход курьера с отсутствием данных
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

        // Добавьте проверки ответа при недостатке данных для входа здесь
    }

    /**
     * Тест для проверки ответа API при использовании несуществующих учетных данных.
     */
    @Test
    public void testCourierLoginWithNonExistingCredentials() {
        // Предположим, что у нас есть несуществующий логин и пароль
        String login = "nonExistingLogin";
        String password = "nonExistingPassword";

        // Выполнение запроса на вход курьера с несуществующими данными
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

        // Добавьте проверки ответа при использовании несуществующих учетных данных здесь
    }
}





