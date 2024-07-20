package API.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BasicMethods extends BaseAPITest {
    /**
     * Метод для преобразования объекта в JSON строку.
     *
     * @param object Объект для преобразования в JSON.
     * @return JSON строка представления объекта.
     */
    protected String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Test1.logger.error("Ошибка преобразования объекта в JSON: {}", e.getMessage());
            return "{}";
        }
    }
}
