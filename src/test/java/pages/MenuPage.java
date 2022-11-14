package pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MenuPage extends PageObject {

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
