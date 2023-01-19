package stepdefs;

import enviroment.Init;
import io.cucumber.java.ru.И;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Evaluator;

public class FieldSteps {

    private static final Logger LOG = LoggerFactory.getLogger(FieldSteps.class);

    @И("^поле \"([^\"]*)\" заполняется значением \"([^\"]*)\"$")
    @Step
    public void fillUpField(String arg0, String value) {
        Init.getCurrentPage().getField(arg0).waitUntilVisible().then().sendKeys(value);
        LOG.info(String.format("Поле [%s] заполняется значением [%s]", (String) Evaluator.getVariable(arg0), value));
    }

    @И("^нажимается кнопка \"([^\"]*)\"$")
    @Step
    public void clickField(String arg0) {
        Init.getCurrentPage().getField(arg0).waitUntilClickable().then().click();
        LOG.info(String.format("И нажимается кнопка [%s]", (String) Evaluator.getVariable(arg0)));
    }

}
