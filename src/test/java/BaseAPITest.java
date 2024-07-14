import org.junit.jupiter.api.BeforeAll;
import io.restassured.RestAssured;

public class BaseAPITest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";    // Замените на базовый URL вашего API
    }
}