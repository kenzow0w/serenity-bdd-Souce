package pages;

import factories.ElementTitle;
import factories.PageEntry;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import service.AbstractPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PageEntry(title = "Menu page")
public class MenuPage extends AbstractPage {

    @ElementTitle(value = "Сart")
    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    WebElement countCart;

    public void shouldBeVisibleCountOnThePage(int count) {
        shouldBeVisible(countCart);
        assertEquals(String.valueOf(count), countCart.getText(), "Кол-во товаров в корзине отображено неверно !!");
    }
}
