package utils;

import groovy.util.Eval;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Optional;

public class UtilElements {

    public static void selectFromDropDownForIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    public static void selectOneElementFromListForIndex(List<WebElementFacade> list, Integer index) {
        Optional<Integer> optional = Optional.of(index);
        list.get(optional.get()).click();
    }

    public static void selectOneElementFromListForText(List<WebElement> list, String text) {
        Optional<String> optional = Optional.of(text);
        try {
            list.stream().filter(e -> e.getText().equals(optional.get())).findFirst().get().click();
        } catch (RuntimeException e) {
            throw new RuntimeException("Element doesn't exist");
        }
    }

    public static WebElement getOneElementFromListForText(List<WebElement> list, String text) {
        Optional<String> optional = Optional.of(Evaluator.getVariable(text));
        try {
            return list.stream().filter(e -> e.getText().equals(optional.get())).findFirst().get();
        } catch (RuntimeException e) {
            throw new RuntimeException("Element doesn't exist");
        }
    }
}
