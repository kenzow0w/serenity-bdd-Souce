package runner;

import io.cucumber.junit.CucumberOptions;
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
