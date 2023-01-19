package pages;

import factories.PageEntry;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.WhenPageOpens;
import service.AbstractPage;
import factories.ElementTitle;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;
import utils.AccountCredentials;

@PageEntry(title = "Страница авторизации")
public class LoginPage extends AbstractPage {

    @ElementTitle("Логин")
    @FindBy(xpath = "//input[@id='user-name']")
    private WebElementFacade usernameField;

    @ElementTitle("Пароль")
    @FindBy(xpath = "//input[@id='password']")
    private WebElementFacade passwordField;

    @ElementTitle("Вход")
    @FindBy(xpath = "//input[@id='login-button']")
    private WebElementFacade loginButton;

    public LoginPage autorization(String login, String password) throws InterruptedException {
        AccountCredentials accountCredentials = new AccountCredentials(login);
        usernameField.sendKeys(login);
        passwordField.sendKeys(password);
        clickOn(loginButton);
        return this;
    }


}
