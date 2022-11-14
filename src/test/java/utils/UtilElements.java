package utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Optional;

public class UtilElements {

    public static void selectFromDropDownForIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    public static void selectOneElementFromListForIndex(List<WebElement> list, Integer index) {
        Optional<Integer> optional = Optional.of(index);
        list.get(optional.get()).click();
    }

    public static void selectOneElementFromListForText(List<WebElement> list, String text) {
        Optional<String> optional = Optional.of(text);
        try {
            for (WebElement element : list) {
                if (element.getText().equals(optional.get())) {
                    element.click();
                    break;
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Element doesn't exist");
        }
    }

}
