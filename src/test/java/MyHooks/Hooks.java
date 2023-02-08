package MyHooks;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.Assume;

public class Hooks {

    @Before(value = "@skip_scenario", order = 0)
    public void skip_scenario(Scenario scenario) {
        System.out.println("I'm here");
        boolean flag = true;
        System.out.println("SKIPPED SCENARIO IS : " + scenario.getName());
        Assume.assumeTrue(flag);
    }

}
