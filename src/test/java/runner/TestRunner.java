package runner;


import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        monochrome = true,
        plugin = {"pretty"},
        glue = "stepdefs",
        features = "classpath:features/"
)
public class TestRunner {
}
