package steps;

import net.thucydides.core.annotations.Step;
import pages.MenuPage;

public class MenuPageSteps {

    MenuPage menuPage;

    @Step("И проверяется, что товаров в корзине нет")
    public void shouldNotBeVisibleCount(){
        menuPage.shouldNotBeVisibleCountOnThePage();
    }

    @Step("И проверяется, что в корзине находится [{0}] товаров")
    public void shouldBeVisibleCountToCart(int count) {
        menuPage.shouldBeVisibleCountOnThePage(count);
    }
}
