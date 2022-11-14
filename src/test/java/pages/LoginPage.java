package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.LoginPageSteps;
import utils.AccountCredentials;

//@DefaultUrl("https://opensource-demo.orangehrmlive.com/")
public class LoginPage extends PageObject {

    @FindBy(xpath = "//input[@id='user-name']")
    private WebElementFacade usernameField;

    @FindBy(xpath = "//input[@id='password']")
    private WebElementFacade passwordField;

    @FindBy(xpath = "//input[@id='login-button']")
    private WebElementFacade loginButton;

    public LoginPage autorization(String login, String password) throws InterruptedException {
        AccountCredentials accountCredentials = new AccountCredentials(login, password);
        usernameField.sendKeys(login);
        passwordField.sendKeys(password);
        clickOn(loginButton);
        return this;
    }


}
