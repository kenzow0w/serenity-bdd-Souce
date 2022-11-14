package steps;

import net.thucydides.core.annotations.Step;
import pages.ItemPage;

public class ItemPageSteps {

    ItemPage itemPage;

    @Step("И нажимаеттся кнопка добавить в корзину")
    public void clickOnAddItemToCart() {
    itemPage.addItemToTheCart();
    }

    @Step("И проверяется, что кнопка [Remove] видна на странице")
    public void shouldBeVisibleElementRemove() {
        itemPage.shouldBeVisibleElementRemove();
    }
}
