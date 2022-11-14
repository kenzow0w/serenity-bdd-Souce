package steps;

import net.thucydides.core.annotations.Step;
import pages.MainPage;

public class MainPageSteps {


    private MainPage mainPage;

    @Step("И изменяется сортировка товаров по [{0}]")
    public void changeLanguage(String text){
        mainPage.selectLanguage(text);
    }

    @Step("И выбирается товар с порядковым номером [{0}]")
    public void chooseOneItemForIndex(int index){
     mainPage.chooseOneItemOnThePageForIndex(index);
    }

    @Step("И выбирается товар с именем [{0}]")
    public void chooseOneItemForName(String name){
        mainPage.chooseOneItemOnThePageForText(name);
    }

}
