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

@PageEntry(title = "Autorizhation page")
public class LoginPage extends AbstractPage {

    @ElementTitle("Username")
    @FindBy(xpath = "//input[@id='user-name']")
    private WebElementFacade usernameField;

    @ElementTitle("Password")
    @FindBy(xpath = "//input[@id='password']")
    private WebElementFacade passwordField;

    @ElementTitle("Login")
    @FindBy(xpath = "//input[@id='login-button']")
    private WebElementFacade loginButton;
}
