package stepdefs;

import io.cucumber.java.ru.*;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Evaluator;

import static utils.Evaluator.getVariable;
import static utils.Evaluator.setVariable;

public class UtilSteps extends PageObject {

    private static Logger LOG = LoggerFactory.getLogger(UtilSteps.class);

    @Дано("^в переменной \"([^\"]*)\" сохраняется значение \"(.*)\"$")
    @Step
    public void initVariable(String variable, String param) {
        String value = getVariable(param);
        setVariable(variable, value);
        LOG.info("В переменной '{}' сохранено значение '{}'", variable, param);
    }

    @И("^вывести в консоль значение переменной \"([^\"]*)\"$")
    @Step
    public void вывестиВКонсольЗначениеПеременной(String arg0) {
        LOG.info((String) Evaluator.getVariable(arg0));
    }


}
