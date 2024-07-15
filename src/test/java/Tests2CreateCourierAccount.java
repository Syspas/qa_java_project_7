import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
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
@Story("Создание учетной записи курьера")
public class Tests2CreateCourierAccount extends BaseAPITest {

    @Step("Создание учетной записи курьера с заданными параметрами")
    @Test
    public void testCreateCourierAccount() {
        String login = "ninja";
        String password = "1234";
        String firstName = "saske";

        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body("{\"login\":\"" + login + "\",\"password\":\"" + password + "\",\"firstName\":\"" + firstName + "\"}")
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true))
                .extract()
                .response();

        System.out.println("Успешное создание учетной записи: " + response.asString());
    }

    @Step("Проверка ответа при создании учетной записи без логина или пароля")
    @Test
    public void testCreateCourierAccountWithoutLoginOrPassword() {
        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body("{\"firstName\":\"saske\"}")
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .extract()
                .response();

        System.out.println("Ответ при создании учетной записи без логина или пароля: " + response.asString());
    }

    @Step("Проверка ответа при создании учетной записи с повторяющимся логином")
    @Test
    public void testCreateCourierAccountWithDuplicateLogin() {
        String login = "ninja";
        String password = "1234";
        String firstName = "saske";

        Response response = given()
                .baseUri(baseURI)
                .contentType("application/json")
                .body("{\"login\":\"" + login + "\",\"password\":\"" + password + "\",\"firstName\":\"" + firstName + "\"}")
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"))
                .extract()
                .response();

        System.out.println("Ответ при создании учетной записи с повторяющимся логином: " + response.asString());
    }
}
