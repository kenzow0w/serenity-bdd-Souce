package factories;

import enviroment.Init;
import exceptions.AutotestError;
import lombok.SneakyThrows;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AbstractPage;
import utils.Evaluator;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class PageFactory {

    private static final Logger LOG = LoggerFactory.getLogger(PageFactory.class);
    private static AbstractPage currentPage;
    private static final String PAGES_PACKAGE = Evaluator.getVariable("#{pages.package}");
    private static String currentPageName;

    private static final Map<String, List<Class<? extends AbstractPage>>> ALL_PAGES = new HashMap<>();
    private static Set<String> pagesWithDuplicatedFields = new HashSet<>();
    private static Set<String> errorPagesWithoutPageEntry = new HashSet<>();
    private static Set<String> errorPagesWithoutAnnotationFields = new HashSet<>();
    private static Set<String> pagesWithDuplicatedName = new HashSet<>();
    private static Reflections reflections = new Reflections(PAGES_PACKAGE);

    public static void init() {
        LOG.info("start init pageFactory");
        createPageList();
        validate();
        finalCheck();
    }

    private static void set(AbstractPage page) {
        currentPage = page;
        LOG.info("Текущая страница {}", page.getClass().getCanonicalName());
    }

    @SneakyThrows
    private static <T> T construct(Class<? extends AbstractPage> clazz) throws TimeoutException {
//        List<Field> fields = FieldFactory.getPageFields.apply(clazz);
        Init.getWebDriver().switchTo().window(Init.getWebDriver().getWindowHandle()); // Добавлено перемещение фокуса - для работы с ЯндексБраузером. Он не активирует автоматически новую открытую вкладку, поэтому необходимо активировать ее вручную.
        System.out.println("Title WebPage = " + Init.getWebDriver().getTitle());
        return (T) clazz.getConstructor().newInstance();
    }

    private static AbstractPage get(String name) throws TimeoutException {
        if (ALL_PAGES.containsKey(name)) {
            final Class<? extends AbstractPage> clazz = ALL_PAGES.get(name).get(0);
            return getPage(clazz);
        } else {
            throw new AutotestError(String.format("Страница [%s] не найдена. Убедитесь в наличии аннотации @PageEntry у этой страницы.", name));
        }
    }

    public static AbstractPage getPage(String name) throws TimeoutException {
        currentPageName = name;
        return get(name);
    }

    public static <T> T getPage(Class<T> clazz) throws TimeoutException {
        final T page = construct((Class<? extends AbstractPage>) clazz);
        set((AbstractPage) page);
        return page;
    }

    public static AbstractPage getCurrentPage() {
        return currentPage;
    }

    /**
     * Сбор всех классов, унаследованных от AbstractPage и имеющих аннотацию @PageEntry в коллекцию ALL_PAGES, где
     * key - название страницы, value - группа страниц вида "главная страница и, при наличии, ее аффилированные страницы"
     */
    private static void createPageList() {
        Predicate<Class<? extends AbstractPage>> hasPageEntry = page -> {
            if (!page.isAnnotationPresent(PageEntry.class)) {
                String errmsg = "Страница [%s] не содержит аннотации @PageEntry. Данная страница будет недоступна при поиске.";
                errorPagesWithoutPageEntry.add(String.format(errmsg, page));
                return false;
            }
            return true;
        };

        Predicate<Class<? extends AbstractPage>> isNotDuplicated = page -> {
            if (ALL_PAGES.put(page.getAnnotation(PageEntry.class).title(), null) != null) {
                String errmsg = "Cтраница [%s] имеет не уникальное название [%s]\n";
                String pageName = page.getAnnotation(PageEntry.class).title();
                pagesWithDuplicatedName.add(String.format(errmsg, page.getCanonicalName(), pageName));
                return false;
            }
            return true;
        };
        Function<Class<? extends AbstractPage>, List<Class<? extends AbstractPage>>> createPageList = page -> {
            List<Class<? extends AbstractPage>> allPages = new ArrayList<>();
            allPages.add(page);
            return allPages;
        };
        Consumer<List<Class<? extends AbstractPage>>> addToAll_Pages = pages ->
                ALL_PAGES.put(pages.get(0).getAnnotation(PageEntry.class).title(), pages);

        reflections.getSubTypesOf(AbstractPage.class).stream().filter(hasPageEntry).filter(isNotDuplicated).map(createPageList).forEach(addToAll_Pages);
    }

    /**
     * Вызов проверок для сформированных групп страниц ALL_PAGES
     */
    private static void validate() {
        /**
         * Получаем все поля со страницы, имеющие аннотацию @ElementTitle или выводим ошибку в LOG при ее отсутствии
         */
        Function<Class<? extends AbstractPage>, List<Field>> getPageFields = page -> {
            Predicate<Field> fieldHasAnnotation = field -> {
                String errmsg = "Страница [%s] содержит поля без аннотации @ElementTitle";
                boolean hasAnnotation = field.isAnnotationPresent(ElementTitle.class);
                if (!hasAnnotation) {
                    errorPagesWithoutAnnotationFields.add(String.format(errmsg, page.getAnnotation(PageEntry.class).title()));
                }
                return hasAnnotation;
            };
            return Arrays.stream(page.getDeclaredFields()).filter(fieldHasAnnotation).collect(Collectors.toList());
        };

        Function<Class<? extends AbstractPage>, String> classGetTitle = page -> page.getAnnotation(PageEntry.class).title();

        /**
         * Проверка на дублирование полей;
         * Если поле дублируется на главной или ее аффилированной страницах, собираем в список pagesWithDuplicatedFields
         * Принимаем во внимание, что ElementTitle полей может быть одинаковым, при разных subElementOf
         */
        Consumer<List<Class<? extends AbstractPage>>> checkDuplicateFields = pages -> {
            String errmsg = "Страница [%s] или её аффилированные страницы содержат поля с одинаковым @ElementTitle: %s\n";
            List<String> duplicateFieldsName = pages.stream()
                    .map(getPageFields).flatMap(Collection::stream)
                    .map(field -> {
                        String title = field.getAnnotation(ElementTitle.class).value();
                        return title;
                    }).collect(Collectors.toList());
            new HashSet<>(duplicateFieldsName).forEach(duplicateFieldsName::remove);
            duplicateFieldsName.forEach(title -> pagesWithDuplicatedFields.add(String.format(errmsg, classGetTitle.apply(pages.get(0)), duplicateFieldsName)));
        };

        /**
         * Вызов всех проверок для каждой страницы из входящей Map
         */
        Consumer<Map.Entry<String, List<Class<? extends AbstractPage>>>> pageCheck = map -> {
            Class<? extends AbstractPage> mainPage = map.getValue().get(0);
            List<Class<? extends AbstractPage>> linkedPages = map.getValue();
            checkDuplicateFields.accept(linkedPages);
        };
        ALL_PAGES.entrySet().forEach(pageCheck);
    }

    /**
     * Вызов ошибки при дублировании полей на страницах или если превышена глубина аффилирования
     */
    private static void finalCheck() {
        List<String> notCriticalError = new ArrayList<>();
        List<String> criticalError = new ArrayList<>();

        criticalError.addAll(pagesWithDuplicatedName);
        criticalError.addAll(pagesWithDuplicatedFields);
        notCriticalError.addAll(errorPagesWithoutAnnotationFields);
        notCriticalError.addAll(errorPagesWithoutPageEntry);
        if (!criticalError.isEmpty()) {
            assertTrue(String.format("Обнаружено дублирование в следующих ииенах [%s]", criticalError.toString()), criticalError.isEmpty());
        }
    }

}
