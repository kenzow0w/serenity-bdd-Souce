package utils;

import io.cucumber.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Evaluator {

    private static final Logger LOG = LoggerFactory.getLogger(Evaluator.class);
    public final static Map<Object, Object> STASH = new HashMap<>();

    private static final String DATATABLE_VAR_PATTERN = "\\#table\\{(?<var>[^{^(^}^)]*)\\}";
    private static final String RANDOM_NUMBER_PATTERN = "\\#\\{\\s*random\\s*\\{(?<random>.*?)\\}\\}";
    private static final String VAR_PATTERN = "\\#\\{(?<var>[^{^(^}^)]*)\\}";

    private static final Pattern VAR_PATTERN_COMPILED = Pattern.compile(VAR_PATTERN);
    private static final Pattern RANDOM_NUMBER_PATTERN_COMPILED = Pattern.compile(RANDOM_NUMBER_PATTERN);
    private static final Pattern DATATABLE_VAR_PATTERN_COMPILED = Pattern.compile(DATATABLE_VAR_PATTERN);
//    public static Map<Object, Object> getBasicStash() {
//        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
//        SerenitySystemProperties list = SerenitySystemProperties.getProperties();
//        list.getValue(ThucydidesSystemProperty.valueOf("webdriver.driver"));
//        Iterator<String> iterator = variables.getKeys().iterator();
//        while (iterator.hasNext()) {
//            for (String key : variables.getKeys()) {
//                System.out.println("key =" +key);
//                System.out.println("value =" +variables.getProperty(key));
//                Evaluator.setVariable(key, variables2);
//                STASH.put(key, variables.getProperty(key));
//            }
//
//        }
//        return STASH;
//    }

    public static void setVariable(String name, Object value) {
        STASH.put(name, value);
    }

    public static boolean containsKey(String key) {
        return STASH.containsKey(key);
    }

    public static <T> T getVariable(String name) throws RuntimeException {
        if (name == null) {
            throw new RuntimeException("method getVariable instanced with value null");
        }
        else if (name.trim().matches(".*" + DATATABLE_VAR_PATTERN + ".*")) {
            Matcher varMatcher = DATATABLE_VAR_PATTERN_COMPILED.matcher(name);
            DataTable varSB = null;
            while (varMatcher.find()) {
                String varName = varMatcher.group("var");
                Object var = STASH.get(varName);
                if (var == null) {
                    var = System.getProperty(varName);
                    if (var != null) STASH.put(varName, var);
                }
                varSB = (DataTable) var;
            }
            return (T) varSB;
        }
        return (T) Optional.ofNullable(evalVariable(name)).get();
    }


    public static <T> T evalVariable(String param) {
        if (param.trim().matches(".*" + VAR_PATTERN + ".*")) {
            Matcher varMatcher = VAR_PATTERN_COMPILED.matcher(param);
            StringBuffer varSB = new StringBuffer();
            while (varMatcher.find()) {
                String name = varMatcher.group("var");
                Object var = STASH.get(name);
                if (var == null) {
                    LOG.error("Переменная [{}] пустая.Проверьте ее", name);
                    var = System.getProperty(name);
                    if (var != null) STASH.put(name, var);
                }
                String value = String.valueOf(var);
                varMatcher.appendReplacement(varSB, Matcher.quoteReplacement(value));
            }
            varMatcher.appendTail(varSB);
            return evalVariable(varSB.toString());
        } else if (param.trim().matches(".*" + RANDOM_NUMBER_PATTERN + ".*")) {
            Matcher randomMatcher = RANDOM_NUMBER_PATTERN_COMPILED.matcher(param);
            StringBuffer randomSB = new StringBuffer();
            String value = "";
            while (randomMatcher.find()) {
                value = String.valueOf(evalRandomNumber(randomMatcher.group(1)));
                randomMatcher.appendReplacement(randomSB, randomMatcher.quoteReplacement(value));
            }
            randomMatcher.appendTail(randomSB);
            return evalVariable(randomSB.toString());
        }
        return (T) param;
    }

    public static DataTable getDataTable(DataTable dataTable) {
        if (dataTable.toString().replaceAll("\\n", "").matches(".*" + DATATABLE_VAR_PATTERN + ".*")) {
            return Evaluator.getVariable(dataTable.toString());
        }
        return dataTable;
    }


    private static String evalRandomNumber(String command) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String[] values = command.split(",");
        int limit;
        String prefix = "", postfix = "";

        if (values.length > 1) {
            prefix = values[0];
            if (values.length == 3) postfix = values[2];
            limit = Integer.valueOf(values[1]) - postfix.length();
        } else {
            limit = Integer.valueOf(values[0]);
        }
        long min = 1111111111111111111L;
        long range = Long.MAX_VALUE - min + 1;
        StringBuilder result = new StringBuilder(prefix);
        while (result.length() < limit) {
            result.append(random.nextLong(range) + min);
        }
        return result.substring(0, limit).concat(postfix);
    }


}