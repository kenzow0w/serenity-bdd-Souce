package service;

import listeners.WebDriverEventHandler;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.WebDriver;
import steps.NavigateActionsSteps;

public class BaseTest extends PageObject {

    @Managed(uniqueSession = true)
    WebDriver driver;

    @Steps
    protected NavigateActionsSteps navigateActionsSteps;

//    protected static EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
}
