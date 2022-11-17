package stepdefs;

import net.serenitybdd.core.steps.UIInteractions;
import net.thucydides.core.annotations.Step;

/**
 * UIInteractionSteps let us define action classes which regroup related actions.
 * The @Step annotation is used to indicate that this action will appear as a step in the reports.
 */
public class NavigateActionsSteps extends UIInteractions {

    @Step("Navigate to the home page")
    public void toTheHomePage() {
        openUrl("http://wikipedia.com/");
    }

    @Step("Navigate to back")
    public void toBack(){
        getDriver().navigate().back();
    }

}
