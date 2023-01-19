package runner;

import enviroment.Stand;
import factories.PageFactory;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runners.model.InitializationError;

public class Runner extends CucumberWithSerenity {
    static {
        Stand.init();
        PageFactory.init();
    }

    public Runner(Class clazz) throws InitializationError {
        super(clazz);
    }
}
