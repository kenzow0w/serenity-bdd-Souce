package stepdefs;

import exceptions.AutotestError;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.*;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Evaluator;
import utils.JsonProcessor;

import java.util.Map;

import static utils.Evaluator.getVariable;
import static utils.Evaluator.setVariable;

public class jsonSteps {

    private static Logger LOG = LoggerFactory.getLogger(jsonSteps.class);

    @И("содержимое JSON файла \"([^\"]*)\" сохраняется в переменной \"([^\"]*)\"$")
    @Step
    public void loadValueToVar(String jsonFileName, String json) {
        Object value = JsonProcessor.getJsonDocument(jsonFileName);
        setVariable(json, value);
        LOG.info("В переменной {} сохранено значение {} ", json, value);
    }

    @И("для сохраненного Json \"([^\"]*)\" устанавливаются значения:$")
    @Step
    public void setJsonValue(String json, DataTable dataTable) {
        Map<String, String> xPathsAndVariables = Evaluator.getDataTable(dataTable).asMap(String.class, String.class);
        xPathsAndVariables.forEach((xpath, variable) ->
        {
            try {
                JsonProcessor.setValueByJsonPath(getVariable(json), xpath, getVariable(variable));
            } catch (IllegalArgumentException e) {
                try {
                    throw new AutotestError(String.format("Не найдена переменная %s. Для начала работы с Json воспользуйтесь шагом \"И содержимое JSON файла сохраняется в переменной\"", json));
                } catch (AutotestError ex) {
                    ex.printStackTrace();
                }
            }
        });
        LOG.info("В переменной {} сохранено значение {} ", json, getVariable(String.format("#{%s}", json)));
    }
}
