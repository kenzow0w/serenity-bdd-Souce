package pages;

import factories.ElementTitle;
import factories.PageEntry;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import service.AbstractPage;


@PageEntry(title = "Страница с товарами")
public class ItemPage extends AbstractPage {

    @ElementTitle("Добавить в корзину")
    @FindBy(xpath = "//button[@id='add-to-cart-sauce-labs-onesie']")
    private WebElement addItem;

    @ElementTitle("Удалить из корзины")
    @FindBy(xpath = "//button[@id='remove-sauce-labs-onesie']")
    private WebElement removeItem;

    public void addItemToTheCart() {
        addItem.click();
    }

    public void shouldBeVisibleElementRemove() {
        shouldBeVisible(removeItem);
    }
}
