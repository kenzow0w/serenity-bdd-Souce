package MyHooks.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemPage extends PageObject {

    @FindBy(xpath = "//button[@id='add-to-cart-sauce-labs-onesie']")
    private WebElement addItem;

    @FindBy(xpath = "//button[@id='remove-sauce-labs-onesie']")
    private WebElement removeItem;

    public void addItemToTheCart() {
        addItem.click();
    }

    public void shouldBeVisibleElementRemove() {
        shouldBeVisible(removeItem);
    }
}
