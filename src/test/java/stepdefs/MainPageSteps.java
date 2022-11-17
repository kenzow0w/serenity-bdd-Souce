package stepdefs;

import io.cucumber.java.ru.*;
import net.thucydides.core.annotations.Step;
import MyHooks.pages.MainPage;
import utils.Evaluator;

public class MainPageSteps {


    private MainPage mainPage;

    @И("изменяется сортировка товаров по {string}")
    @Step
    public void changeLanguage(String text){
        mainPage.selectLanguage(text);
    }

    @И("выбирается товар с порядковым номером {int}")
    @Step
    public void chooseOneItemForIndex(int index){
     mainPage.chooseOneItemOnThePageForIndex(index);
    }

    @И("выбирается товар с именем в переменной {string}")
    @Step
    public void chooseOneItemForName(String name){
        mainPage.chooseOneItemOnThePageForText(Evaluator.getVariable(name));
    }

}
