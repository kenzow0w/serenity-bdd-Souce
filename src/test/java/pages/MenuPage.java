package pages;

import factories.PageEntry;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import service.AbstractPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PageEntry(title = "Страница меню")
public class MenuPage extends AbstractPage {

    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    WebElement countCart;

    public void shouldNotBeVisibleCountOnThePage(){
        shouldNotBeVisible(countCart);
    }

    public void shouldBeVisibleCountOnThePage(int count) {
        shouldBeVisible(countCart);
        assertEquals(String.valueOf(count), countCart.getText(), "Кол-во товаров в корзине отображено неверно !!");
    }
}
