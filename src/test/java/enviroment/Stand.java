package enviroment;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Evaluator;
import utils.UTF8Control;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Stand {

    private static final String ERROR_MESSAGE = "Не обнаружен файл src\\test\\resources\\configs\\config.properties c настройками";
    private static final Logger LOG = LoggerFactory.getLogger(Stand.class);
    private static final Locale stand = new Locale(System.getProperty("properties.bundle", "default"));
    private static ResourceBundle config;

    private Stand() {
    }

    public static void init() {
        try {
            config = ResourceBundle.getBundle("config", stand, new UTF8Control());
            pullAllInStash(config);
            initProperties();
        } catch (MissingResourceException e) {
            LOG.debug(ERROR_MESSAGE);
        }
    }

    private static void initProperties() {
        String[] propNames = config.getString("upload.properties.to.stash").split(",");
        for (String name : propNames) {
            try {
                pullAllInStash(ResourceBundle.getBundle(name, stand, new UTF8Control()));
            } catch (MissingResourceException e) {
                LOG.debug("Попытка загрузить {}.properties не удалась", name);
            }
        }
    }

    private static void pullAllInStash(ResourceBundle config) {
        Enumeration<String> keys = config.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            Evaluator.setVariable(key, config.getString(key));
        }
    }
}
