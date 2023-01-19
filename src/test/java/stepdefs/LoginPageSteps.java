package stepdefs;

import io.cucumber.java.ru.*;
import lombok.SneakyThrows;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.LoginPage;

public class LoginPageSteps {

    private static Logger LOG = LoggerFactory.getLogger(LoginPageSteps.class);

    private LoginPage loginPage;

    @Когда("открывается страница авторизации")
    @Step
    public void isOnLoginPage(){
        LOG.info(String.format("Open page: ", Serenity.getCurrentSession().getMetaData().get("webdriver.base.url")));
        loginPage.open();
    }

    @SneakyThrows
    @Затем("выполняется авторизация юзера с именем {string}, и паролем {string}")
    @Step
    public void loginAsUser(String login, String password)
    {
        loginPage.autorization(login, password);
    }

}
