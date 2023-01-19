package service;

import factories.FieldFactory;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPage extends PageObject {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractPage.class);

    public WebElementFacade getField(String name) {
        return FieldFactory.getField(name, this.getClass());
    }

}
