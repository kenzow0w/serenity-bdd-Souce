package utils;

import enviroment.Init;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class DriverWait extends FluentWait<WebDriver> {

    private static final int DEFAULT_INTERVAL = 10;

    private DriverWait(WebDriver driver) {
        super(driver);
    }

    public static DriverWait build() {
        return new DriverWait(Init.getWebDriver());
    }

    public static DriverWait shortTimeout() {
        return withTimeout(30);
    }

    private static DriverWait withTimeout(long i) {
        return (DriverWait) build().withTimeout(Duration.ofSeconds(i))
                .pollingEvery(Duration.ofSeconds(DEFAULT_INTERVAL))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NotFoundException.class);
    }

    @Override
    public DriverWait pollingEvery(Duration duration) {
        return (DriverWait) super.pollingEvery(duration);
    }

    @Override
    public DriverWait ignoring(Class<? extends Throwable> exceptionType) {
        return (DriverWait) super.ignoring(exceptionType);
    }
}
