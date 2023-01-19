package enviroment;

import factories.PageFactory;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AbstractPage;

public class Init extends PageObject {

    private static final Logger LOG = LoggerFactory.getLogger(Init.class);
    private static PageFactory pageFactory;

    public static PageFactory getPageFactory() {
        if (null == pageFactory) {
            pageFactory = new PageFactory();
        }
        return pageFactory;
    }

    public static WebDriver getWebDriver() {
        return ThucydidesWebDriverSupport.getDriver();
    }

    public static AbstractPage getCurrentPage() {
        return pageFactory.getCurrentPage();
    }

    public static void closeDriver() {
        if (ThucydidesWebDriverSupport.getWebdriverManager().getActiveWebdriverCount() > 0) {
            ThucydidesWebDriverSupport.closeDriver();
        }
    }

}