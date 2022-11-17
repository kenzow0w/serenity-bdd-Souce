package stepdefs.restApiSteps;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import exceptions.AutotestError;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.*;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;
import org.junit.jupiter.api.Assertions;
import org.slf4j.LoggerFactory;
import utils.restassured.RestAssuredWrapper;
import utils.Evaluator;
import org.slf4j.Logger;

import java.util.*;

import static utils.Evaluator.getDataTable;
import static utils.Evaluator.getVariable;

public class RestApiSteps extends PageObject {

    private static Logger LOG = LoggerFactory.getLogger(RestApiSteps.class);

    @SneakyThrows
    @И("^отправляется (POST|GET|DELETE|PUT) запрос, с названием \"([^\"]*)\" и параметрами:$")
    @Step
    public static void sendPost(String requestType, String requestName, DataTable dataTable) {
        List<List<String>> params = dataTable.asLists(String.class);
        String url = "";
        String body = "";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> formParams = new HashMap<>();
        List<Cookie> cookiesList = new ArrayList<>();

        for (List<String> param : params) {
            String key = param.get(0);
            String value = param.get(1);
            if (key.equals("url")) {
                url = getVariable(value);
            } else if (key.equals("body")) {
                body = getVariable(value);
            } else if (key.startsWith("[formParam]")) {
                String formParamName = key.substring("[formParam]".length());
                formParams.put(formParamName, value);
            } else if (key.startsWith("[header]") && !key.equalsIgnoreCase("[header]cookie")) {
                String headerName = key.substring("[header]".length());
                headers.put(headerName, value);
            }
        }
        Response response = RestAssuredWrapper.sendRequest(requestType, url, headers, formParams, body);
        RestAssuredWrapper.putResponse(requestName, response);
    }

    @SneakyThrows
    @И("^для REST-запроса \"([^\"]*)\" код ответа равен \"([^\"]*)\"$")
    @Step
    public static void checkResponseCode(String requestName, String expectedCode) {
        Response response = RestAssuredWrapper.getResponse(requestName);
        int statusCode = response.then().extract().statusCode();
        Assertions.assertTrue(Integer.parseInt(expectedCode) == statusCode,
                String.format("Код ответа [%s], не равен ожидаемому [%s]", statusCode, expectedCode));
    }

    @SneakyThrows
    @Step
    @И("^для REST-запроса \"([^\"]*)\" сохраняется тело ответа в переменную \"([^\"]*)\"$")
    public static void saveResponseBodyToStash(String requestName, String variableName) {
        Response response = RestAssuredWrapper.getResponse(requestName);
        String responseBody = response.asString();
        Evaluator.setVariable(variableName, responseBody);
    }

    @SneakyThrows
    @И("^для REST-запроса \"([^\"]*)\" проверяется тело ответа на наличие значений по jsonPath:$")
    @Step
    public static void checkResponseValuesByJsonPath(String requestName, DataTable dataTable) {
        Response response = RestAssuredWrapper.getResponse(requestName);
        String responseBody = response.asString();
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        for (String key : data.keySet()) {
            DocumentContext jsonContext = JsonPath.parse(responseBody);
            String responseValue;
            try {
                responseValue = jsonContext.read(key).toString();
            } catch (PathNotFoundException e) {
                throw new AutotestError(String.format("Не найдено ни одного элемента по jsonPath [%s], проверьте правильность запроса", key));
            }
            String dataTableValue = data.get(key).contains("#{") ? getVariable(data.get(key)) : data.get(key);
            if (responseValue.equals(dataTableValue)) {
                LOG.info(String.format("Элемент найденный по jsonPath [%s] равен значению [%s]", key, responseValue));
            } else {
                throw new AutotestError(String.format("Элемент найденный по jsonPath [%s] равен значению [%s], вместо ожидаемого значения [%s]", key, responseValue, dataTableValue));
            }
        }

    }

    @SneakyThrows
    public static void saveValueFromJsonInVariable(String requestName, String jsonPath, String variableName) {
        Response response = RestAssuredWrapper.getResponse(requestName);
        String responseBody = response.asString();

    }

}
