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

}
