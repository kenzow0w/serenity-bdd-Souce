package pages;

import factories.ElementTitle;
import factories.PageEntry;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import service.AbstractPage;


@PageEntry(title = "Items page")
public class ItemPage extends AbstractPage {

    @ElementTitle("add to cart")
    @FindBy(xpath = "//button[@id='add-to-cart-sauce-labs-onesie']")
    private WebElement addItem;

    @ElementTitle("remove from cart")
    @FindBy(xpath = "//button[@id='remove-sauce-labs-onesie']")
    private WebElement removeItem;

    public void addItemToTheCart() {
        addItem.click();
    }

    public void shouldBeVisibleElementRemove() {
        shouldBeVisible(removeItem);
    }
}
