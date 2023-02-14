package pages;

import factories.ElementTitle;
import factories.PageEntry;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import service.AbstractPage;


@PageEntry(title = "Items page")
public class ItemPage extends AbstractPage {

    @ElementTitle("add to cart")
    @FindBy(xpath = "//button[contains(text(), 'Add to cart')]")
    private WebElement addItem;

    @ElementTitle("remove from cart")
    @FindBy(xpath = "//button[contains(text(), 'Remove')]")
    private WebElement removeItem;
}
