package appTests.APItests;


import net.minidev.json.JSONObject;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BaseTest;
import steps.restApiSteps.RestApiSteps;

import java.util.HashMap;
import java.util.Map;

import static steps.restApiSteps.RestApiSteps.*;

@Tag("API")
@ExtendWith(SerenityJUnit5Extension.class)
public class ApiUsersTest extends BaseTest {

    private static Logger LOG = LoggerFactory.getLogger(ApiUsersTest.class);
    private static Map<String, String> parameters = new HashMap<>();
    private static Map<String, String> formParams = new HashMap<>();
    private static Map<String, String> headers = new HashMap<>();
    private static Map<String, String> jsonParam = new HashMap<>();

    public static void clearStash(){
        parameters.clear();
        formParams.clear();
        headers.clear();
        jsonParam.clear();
    }

    @Steps(shared = true)
    RestApiSteps restApiSteps;


//    @WithTag("API")
    @DisplayName("Проверяем получение данных о юзерах через GET-запрос")
    @Test
    public void testGetQueryForUser() {
        clearStash();
        parameters.put("url", "https://reqres.in/api/users");
        formParams.put("page", "2");
        jsonParam.put("$.data[1].first_name", "Lindsay");
        jsonParam.put("$.page", "2");

        sendPost("GET", "getUser", parameters, headers, formParams);
        checkResponseCode("getUser", "200");
        checkResponseValuesByJsonPath("getUser", jsonParam);
    }

    @DisplayName("Проверяем POST-запрос и создание User'a")
    @Test
    public void sendPostRequestAndCreateUser(){
        clearStash();
        int randomNumber = (int) (Math.random() * (130 + 1)) + 10;
        String name = "User-"+ randomNumber;
        String job = "JOB: #"+ randomNumber;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("job", job);
        parameters.put("url", "https://reqres.in/api/users");
        parameters.put("body", jsonObject.toJSONString());
        headers.put("Content-Type", "application/json");
        jsonParam.put("$.name", name);
        jsonParam.put("$.job", job);


        sendPost("POST", "createUser", parameters, headers, formParams);
        checkResponseCode("createUser", "201");
        checkResponseValuesByJsonPath("createUser", jsonParam);
    }


}
