package stepdefs;

import io.cucumber.java.ru.*;
import net.thucydides.core.annotations.Step;
import MyHooks.pages.ItemPage;

public class ItemPageSteps {

    ItemPage itemPage;

    @И("нажимается кнопка добавить товар в корзину")
    @Step
    public void clickOnAddItemToCart() {
    itemPage.addItemToTheCart();
    }

    @И("проверяется, что кнопка [Remove] видна на странице")
    @Step
    public void shouldBeVisibleElementRemove() {
        itemPage.shouldBeVisibleElementRemove();
    }
}
