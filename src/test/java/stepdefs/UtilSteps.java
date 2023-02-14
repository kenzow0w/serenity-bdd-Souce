package stepdefs;

import io.cucumber.java.ru.*;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Evaluator;

import static enviroment.Init.getCurrentPage;
import static utils.Evaluator.getVariable;
import static utils.Evaluator.setVariable;

public class UtilSteps extends PageObject {

    private static Logger LOG = LoggerFactory.getLogger(UtilSteps.class);

    @Дано("^текущий пользователь \"([^\"]*)\"$")
    @Step
    public void setCurrentUser(String user) {
        setVariable("currentUser", user);
    }

    @Дано("^в переменной \"([^\"]*)\" сохраняется значение \"(.*)\"$")
    @Step
    public void initVariable(String variable, String param) {
        String value = getVariable(param);
        setVariable(variable, value);
        LOG.info("В переменной '{}' сохранено значение '{}'", variable, param);
    }

    @И("^вывести в консоль значение переменной \"([^\"]*)\"$")
    @Step
    public void printTerminal(String arg0) {
        LOG.info((String) Evaluator.getVariable(arg0));
    }

    @И("^проверяется (не|)видимость элемента \"([^\"]*)\"$")
    @Step
    public void checkVisibleOfElement(String visible, String fieldTitle) {
        boolean isVisible = visible.isEmpty();
        boolean checkedProperty;
        try {
            checkedProperty = getCurrentPage().getField(fieldTitle).shouldBeVisible().isVisible();
        } catch (RuntimeException e) {
            checkedProperty = false;
        }
        Assert.assertEquals("Element exist/doesn't exist", isVisible, checkedProperty);
        LOG.info(String.format("Element '%s' %s отображается на странице", fieldTitle, visible));
    }
}
