package listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebDriverEventHandler implements WebDriverListener {

    protected static final Logger LOG = LoggerFactory.getLogger(WebDriverEventHandler.class);
//    private boolean isHighLighter = ConfigReader.PROPERTY_CONFIG.highLighter();

    public static WebDriver registerWebDriverEventListener(WebDriver webDriver) {
        WebDriverEventHandler webDriverEventHandler = new WebDriverEventHandler();
        EventFiringDecorator eventFiringDecorator = new EventFiringDecorator(webDriverEventHandler);
        return eventFiringDecorator.decorate(webDriver);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        WebDriverListener.super.beforeSendKeys(element, keysToSend);
        String regex = "^.{80}";
        LOG.debug("In field put [{}]", keysToSend);
    }

    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        System.out.println("error");
        WebDriverListener.super.onError(target, method, args, e);
    }

    @Override
    public void beforeGet(WebDriver driver, String url) {
        WebDriverListener.super.beforeGet(driver, url);
        LOG.info("Go to link: [{}]", url);
    }

    @Override
    public void beforeClick(WebElement element) {
        WebDriverListener.super.beforeClick(element);
        LOG.info("Click button which name is [{}]", element.getText());
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        WebDriverListener.super.afterFindElement(driver, locator, result);
        String regex = "^.{80}";
        LOG.debug("Found element with {}", result.toString().replaceAll(regex, "").replaceAll(".{2}$", ""));
//        if (isHighLighter == true) {
//            HighLighter.highLightElement(driver, result);
//        }
    }

}
