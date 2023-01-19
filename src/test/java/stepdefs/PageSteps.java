package stepdefs;

import enviroment.Init;
import exceptions.AutotestError;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AbstractPage;
import utils.AccountCredentials;
import utils.Evaluator;

import java.util.ArrayList;

import static utils.Evaluator.getVariable;


public class PageSteps extends AbstractPage {

    private static final Logger LOG = LoggerFactory.getLogger(PageSteps.class);

    @Дано("^пользователь переходит на страницу авторизации(?: \"([^\"]*)\")?$")
    @Step
    public void goToLoginPageBySystemName(String subsystemName) {
        String appURL = subsystemName != null ?
                Evaluator.getVariable(String.format("#{%s.url}", subsystemName)) :
                Serenity.environmentVariables().getProperty(ThucydidesSystemProperty.WEBDRIVER_BASE_URL);
        LOG.debug("Загружается URL {}", appURL);
        WebDriver driver = Init.getWebDriver();
        driver.get(appURL);
        LOG.debug("Загружен URL {}", appURL);

        // записываем в лог UserAgent браузера, необходимо для определения типа браузера при запуске в headless режиме
        String uAgent = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
        LOG.debug("Browser UserAgent: " + uAgent);
    }

    @И("^открывается \"([^\"]*)\"$")
    @Step
    public void checkPageIsOpened(String pageName) {
        pageName = getVariable(pageName);
        boolean pageIsOpened = true;
        AbstractPage page = null;
        try {
            page = Init.getPageFactory().getPage(pageName);
        } catch (TimeoutException e) {
            try {
                page = Init.getPageFactory().getPage(pageName);
            } catch (TimeoutException ex) {
                pageIsOpened = false;
            }
        }
        if (pageIsOpened) {
            final AbstractPage page1 = page;
        }
        if (!pageIsOpened)
            throw new AutotestError(String.format("Страница %s не открылась", pageName));
    }

    @И("^пользователь переходит на открывшуюся вкладку$")
    @Step
    public void switchOnNew_Tab() {
        ArrayList<String> windows = new ArrayList<>(Init.getWebDriver().getWindowHandles());
        Init.getWebDriver().switchTo().window(windows.get(windows.size() - 1));
    }

    @И("^пользователь авторизуется как \"([^\"]*)\"$")
    @Step
    public void userAuthorized(String role) {
        final FieldSteps FIELD_STEPS = new FieldSteps();
        AccountCredentials accountCredentials = new AccountCredentials(getVariable(role));
        FIELD_STEPS.fillUpField("Логин", accountCredentials.getLogin());
        FIELD_STEPS.fillUpField("Пароль", accountCredentials.getPassword());
        FIELD_STEPS.clickField("Вход");
    }

    @И("^пользователь завершает текущий сеанс$")
    @Step
    public void closeSession() {
        Init.closeDriver();
    }

}
