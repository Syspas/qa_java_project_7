import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class Test_13_MetroStationSearchUtils {

    /**
     * Поиск станции метро по названию "Сокол"
     */
    @Test
    public void testSearchMetroStation() {


        Response response = given()
                .baseUri(baseURI)
                .queryParam("s", "Сокол")
                .when()
                .get("/api/v1/stations/search")
                .then()
                .extract()
                .response();

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
