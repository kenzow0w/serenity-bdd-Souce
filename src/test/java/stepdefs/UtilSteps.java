package stepdefs;

import enviroment.Init;
import io.cucumber.java.ru.*;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.bootstrap.ThucydidesAgent;
import net.thucydides.core.webdriver.ThucydidesConfigurationException;
import net.thucydides.core.webdriver.ThucydidesWebDriverEventListener;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.junit.guice.ThucydidesJUnitModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Evaluator;

import java.util.ArrayList;

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
    public void вывестиВКонсольЗначениеПеременной(String arg0) {
        LOG.info((String) Evaluator.getVariable(arg0));
    }
}
