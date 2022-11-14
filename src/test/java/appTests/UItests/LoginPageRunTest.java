package appTests.UItests;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import service.BaseTest;
import steps.ItemPageSteps;
import steps.LoginPageSteps;
import steps.MainPageSteps;
import steps.MenuPageSteps;
import utils.Evaluator;


@ExtendWith(SerenityJUnit5Extension.class)
public class LoginPageRunTest extends BaseTest {

    @Steps
    LoginPageSteps loginPageSteps;

    @Steps
    MainPageSteps mainPageSteps;

    @Steps
    MenuPageSteps menuPageSteps;

    @Steps
    ItemPageSteps itemPageSteps;

//    @Pending
    @DisplayName("Проверяем авторизацию пользователя, добавление товара в корзину")
    @Test
    public void testLoginPageTest(){
        loginPageSteps.isOnLoginPage();
        loginPageSteps.loginAsUser("standard_user", "secret_sauce");
        mainPageSteps.changeLanguage("Name (Z to A)");
        Evaluator.setVariable("MyItem", "Sauce Labs Onesie");
        mainPageSteps.chooseOneItemForName(Evaluator.getVariable("MyItem"));
        menuPageSteps.shouldNotBeVisibleCount();
        itemPageSteps.clickOnAddItemToCart();
        itemPageSteps.shouldBeVisibleElementRemove();
        menuPageSteps.shouldBeVisibleCountToCart(1);
    }
}
