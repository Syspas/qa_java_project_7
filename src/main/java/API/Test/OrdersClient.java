package API.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrdersClient {

    /**
     * Метод для принятия заказа.
     *
     * @param orderId Идентификатор заказа.
     * @param courierId Идентификатор курьера.
     * @param BASE_URL URL API
     * @return Ответ API при попытке принять заказ.
     */
    public Response acceptOrder(int orderId, int courierId, String BASE_URL) {
        String endpoint = String.format("%s/%d/accept", BASE_URL, orderId);

        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Courier-ID", courierId) // Добавьте заголовок с идентификатором курьера, если требуется
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response(); // Изменение для более явного получения ответа
    }
}
