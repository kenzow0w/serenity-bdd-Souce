package pages;

import factories.ElementTitle;
import factories.PageEntry;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import service.AbstractPage;

import java.util.List;

import static utils.UtilElements.selectOneElementFromListForIndex;
import static utils.UtilElements.selectOneElementFromListForText;

@PageEntry(title = "Main store's page")
public class MainPage extends AbstractPage {

    @ElementTitle(value = "List for sort items")
    @FindBy(xpath = "//select[@class='product_sort_container']")
    WebElement changeLanguage;

    @ElementTitle(value = "Inventory item name")
    @FindBy(xpath = "//div[@class='inventory_item_name']")
    private List<WebElement> items;

    public void selectLanguage(String text) {
        selectFromDropdown(changeLanguage, text);
    }

    public void chooseOneItemOnThePageForIndex(int index) {
        selectOneElementFromListForIndex(items, index);
    }
}
