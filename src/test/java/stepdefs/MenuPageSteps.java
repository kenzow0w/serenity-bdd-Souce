package stepdefs;

import io.cucumber.java.ru.*;
import net.thucydides.core.annotations.Step;
import pages.MenuPage;

public class MenuPageSteps {

    MenuPage menuPage;

    @И("проверяется, что корзина пуста")
    @Step
    public void shouldNotBeVisibleCount(){
        menuPage.shouldNotBeVisibleCountOnThePage();
    }

    @И("^проверяется, что в корзине находится (\\d+) това(?:р|ра|ов)$")
    @Step
    public void shouldBeVisibleCountToCart(int count) {
        menuPage.shouldBeVisibleCountOnThePage(count);
    }
}
