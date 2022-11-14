package utils.restassured;

import exceptions.AutotestError;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.rest.RestRequests.given;

public class RestAssuredWrapper {

    private static Map<String, Response> responses = new HashMap<>(); //Хранит в себе коллекцию выполненных запросов

    /**
     * Отправляет http запрос с заданными параметрами
     *
     * @param requestType тип отправляемого запроса
     * @param url         URL запроса Включает в себя адрес хоста, порт и параметры запроса
     * @param headers     Хедеры запроса (Не обязательный параметр)
     * @param formParams  Данные формы (Не обязательный параметр)
//     * @param formName    Имя формы (Не обязательный параметр)
     * @param body        Тело запроса (Не обязательный параметр)
//     * @param cookies     Cookies запроса (Не обязательный параметр)
     * @return Объект ответа типа Response
     */
    public static Response sendRequest(String requestType, String url, Map<String, String> headers, Map<String, String> formParams, String body) throws AutotestError {

        RequestSpecification rs = given()
                .log().all()
                .headers(headers)
                .formParams(formParams);
        if (!body.equals("")) {
            rs.body(body);
        }
        Response response;
        switch (requestType.toLowerCase()) {
            case "get":
                response = rs.get(url).peek();
                break;
            case "post":
                response = rs.post(url).peek();
                break;
            case "put":
                response = rs.put(url).peek();
                break;
            case "delete":
                response = rs.delete(url).peek();
                break;
            default:
                throw new AutotestError(String.format("Тип запроса [%s] не определен!", requestType));
        }
        return response;
    }

    /**
     * Достает по ключу результаты запроса из коллекции выполненных запросов.
     * В случае отсутствия искомого ключа, выбрасывает ошибку
     *
     * @param requestName название выполненного запроса
     * @return объект Response
     */
    @SneakyThrows
    public static Response getResponse(String requestName) throws AutotestError {
        Response response = responses.get(requestName);
        if (response != null) {
            return response;
        } else {
            throw new AutotestError(String.format("Запрос с названием [%s] не найден! Убедитесь, что запрос с таким названием был объявлен, а также проверьте правильность названия запроса", requestName));
        }
    }

    public static void putResponse(String requestName, Response response) {
        RestAssuredWrapper.responses.put(requestName, response);
    }
}
