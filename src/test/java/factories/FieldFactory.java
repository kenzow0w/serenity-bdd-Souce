package factories;

import enviroment.Init;
import exceptions.AutotestError;
import lombok.SneakyThrows;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AbstractPage;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldFactory extends PageObject {

    private static final Logger LOG = LoggerFactory.getLogger(FieldFactory.class);

    private static final Map<Class<? extends AbstractPage>, List<List<Field>>> PAGES_FIELDS = new HashMap<>();
    private static final int FIELD_LIST = 1;

    @SneakyThrows
    public static WebElementFacade getField(String name, Class<? extends AbstractPage> page) {
        final int FIRST_FIELD = 0;
        checkPageInPagesFields.accept(page);
        try {
            return fieldFromPageStream.apply(page, name)
                    .map(fieldToWebElementFacade)
                    .collect(Collectors.toList()).get(FIRST_FIELD);
        } catch (IndexOutOfBoundsException e) {
            throw new AutotestError(String.format("Поле [%s] не объявлено на странице ", name));
        }
    }

    /**
     * Преобразование Field в WebElementFacade
     */
    private static Function<Field, WebElementFacade> fieldToWebElementFacade = field ->
            WebElementFacadeImpl.wrapWebElement(Init.getWebDriver(),
                    Init.getWebDriver().findElement(By.xpath(field.getAnnotation(FindBy.class).xpath())));

    /**
     * Получение со страницы page
     * списка всех полей, имеющих аннотацию @ElementTitle
     */
    public static Function<Class<? extends AbstractPage>, List<Field>> getPageFields = page -> {
        Predicate<Field> fieldHasAnnotation = field -> field.isAnnotationPresent(ElementTitle.class);
        return Arrays.stream(page.getDeclaredFields()).filter(fieldHasAnnotation).collect(Collectors.toList());
    };

    /**
     * Проверка ключей PAGES_FIELDS на наличие в них page;
     * В случае если Map не содержит ключа page добавляем новую пару ключ-значение;
     * Где ключ - page;
     * Где значение - список, содержащий два списка полей с page:
     * первый список содержит в себе все поля с page
     */
    private static Consumer<Class<? extends AbstractPage>> checkPageInPagesFields = page -> {
        if (!PAGES_FIELDS.containsKey(page)) {
            List<List<Field>> allFields = new ArrayList<>();
            List<Field> fieldsFromPage = getPageFields.apply(page);
            allFields.add(fieldsFromPage);


            PAGES_FIELDS.put(page, allFields);
        }
    };

    /**
     * Получение стрима Field, которые содержатся на page,
     * имеющих значение @ElementTitle равным name
     */
    private static BiFunction<Class<? extends AbstractPage>, String, Stream<Field>> fieldFromPageStream = (page, name) ->
            PAGES_FIELDS.get(page).get(0).stream()
                    .filter(field -> field.getAnnotation(ElementTitle.class).value().equals(name));
}