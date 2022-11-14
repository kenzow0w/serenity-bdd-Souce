package steps;

import lombok.SneakyThrows;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.LoginPage;

public class LoginPageSteps {

    private static Logger LOG = LoggerFactory.getLogger(LoginPageSteps.class);

    private LoginPage loginPage;

    @Step("Открывается страница авторизации")
    public void isOnLoginPage(){
        LOG.info("Open page");
        loginPage.open();
    }

    @SneakyThrows
    @Step("Выполняется авторизация как юзер")
    public void loginAsUser(String login, String password)
    {
        loginPage.autorization(login, password);
    }
}
