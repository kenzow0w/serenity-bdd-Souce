package service;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.ClearCookiesPolicy;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.WebDriver;
import stepdefs.NavigateActionsSteps;

public class BaseTest extends PageObject {

    @Managed(uniqueSession = true, clearCookies = ClearCookiesPolicy.BeforeEachTest)
    WebDriver driver;

    @Steps
    protected NavigateActionsSteps navigateActionsSteps;


//    protected static EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
}
