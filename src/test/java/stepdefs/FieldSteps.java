package stepdefs;

import enviroment.Init;
import io.cucumber.java.ru.И;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Evaluator;
import utils.UtilElements;

import java.util.List;

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

    @И("^из списка \"([^\"]*)\" выбирается элемент с текстом \"([^\"]*)\"$")
    @Step
    public void clickField(String list, String text) {
        Init.getCurrentPage().getField(list).waitUntilClickable().then().selectByVisibleText(text);
        LOG.info(String.format("Из списка [%s] выбирается элемент с текстом [%s]", (String) Evaluator.getVariable(list), text));
    }

    @И("^прокручиваем страницу до элемента \"([^\"]*)\"$")
    public void scrollIntoViewElement(String fieldTitle) {
        WebElementFacade webElementFacade = Init.getCurrentPage().getField(fieldTitle);
        JavascriptExecutor executor = (JavascriptExecutor) Init.getWebDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", webElementFacade);
    }

    @И("^прокручиваем страницу до элемента из списка \"([^\"]*)\" с текстом из переменной \"([^\"]*)\"$")
    public void scrollIntoViewElement(String fieldTitle, String text) {
        List<WebElement> list = Init.getWebDriver().findElements(By.xpath(Init.getCurrentPage().getXpath(fieldTitle)));
        JavascriptExecutor executor = (JavascriptExecutor) Init.getWebDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", UtilElements.getOneElementFromListForText(list,text));
        UtilElements.getOneElementFromListForText(list,text).click();
    }


}
