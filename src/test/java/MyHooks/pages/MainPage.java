package MyHooks.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static utils.UtilElements.selectOneElementFromListForIndex;
import static utils.UtilElements.selectOneElementFromListForText;

public class MainPage extends PageObject {

    @FindBy(xpath = "//select[@class='product_sort_container']")
    WebElement changeLanguage;

    @FindBy(xpath = "//div[@class='inventory_item_name']")
    private List<WebElement> items;

    public void selectLanguage(String text) {
        selectFromDropdown(changeLanguage, text);
    }

    public void chooseOneItemOnThePageForIndex(int index) {
        selectOneElementFromListForIndex(items, index);
    }

    public void chooseOneItemOnThePageForText(String text){
        selectOneElementFromListForText(items, text);
    }
}
