package steps.restApiSteps;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import exceptions.AutotestError;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;
import org.junit.jupiter.api.Assertions;
import org.slf4j.LoggerFactory;
import utils.restassured.RestAssuredWrapper;
import utils.Evaluator;
import org.slf4j.Logger;

import java.util.Map;

public class RestApiSteps extends PageObject {

    private static Logger LOG = LoggerFactory.getLogger(RestApiSteps.class);

    @SneakyThrows
    @Step("Отправляется запрос {0}, с именем {1} ")
    public static void sendPost(String requestType, String requestName, Map<String, String> parameters, Map<String, String> headers, Map<String, String> formParam) {
        String url = "";
        String body = "";
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (entry.getKey().equals("url")) {
                url = entry.getValue();
            } else if (entry.getKey().equals("body")) {
                body = entry.getValue();
            }
        }
        Response response = RestAssuredWrapper.sendRequest(requestType, url, headers, formParam, body);
        RestAssuredWrapper.putResponse(requestName, response);
    }

    @SneakyThrows
    @Step("И проверяется код ответа {1} в запросе {0}")
    public static void checkResponseCode(String requestName, String expectedCode) {
        Response response = RestAssuredWrapper.getResponse(requestName);
        int statusCode = response.then().extract().statusCode();
        Assertions.assertTrue(Integer.parseInt(expectedCode) == statusCode,
                String.format("Код ответа [%s], не равен ожидаемому [%s]", statusCode, expectedCode));
    }

    @SneakyThrows
    @Step
    public static void saveResponseBodyToStash(String requestName, String variableName) {
        Response response = RestAssuredWrapper.getResponse(requestName);
        String responseBody = response.asString();
        Evaluator.setVariable(variableName, responseBody);
    }

    @SneakyThrows
    @Step
    public static void checkResponseValuesByJsonPath(String requestName, Map<String, String> map) {
        Response response = RestAssuredWrapper.getResponse(requestName);
        String responseBody = response.asString();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            DocumentContext jsonContext = JsonPath.parse(responseBody);
            String responseValue;
            try {
                responseValue = jsonContext.read(entry.getKey()).toString();
            } catch (PathNotFoundException e) {
                throw new AutotestError(String.format("Не найдено ни одного элемента по jsonPath [%s], проверьте правильность запроса", entry.getKey()));
            }
            if(entry.getValue().equals(responseValue)){
                LOG.info(String.format("Элемент найденный по jsonPath [%s] равен значению [%s]", entry.getKey(), responseValue));
            }else {
                throw new AutotestError(String.format("Элемент найденный по jsonPath [%s] равен значению [%s], вместо ожидаемого значения [%s]", entry.getKey(), responseValue, entry.getValue()));
            }
        }

    }

    public static void saveValueFromJsonInVariable(String json, String jsonPath, String variableName) {
        String value;

    }

}
