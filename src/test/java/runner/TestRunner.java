package runner;


import enviroment.Stand;
import factories.PageFactory;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(Runner.class)
@CucumberOptions(
        monochrome = true,
        plugin = {"pretty"},
        glue = "stepdefs",
        features = "classpath:features/",
        tags = "@UI"
)
public class TestRunner {
}
