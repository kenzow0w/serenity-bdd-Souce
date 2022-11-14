package utils;

import net.serenitybdd.core.SerenitySystemProperties;
import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Evaluator {

    public final static Map<Object, Object> STASH = new HashMap<>();

    private static final String RANDOM_NUMBER_PATTERN = "\\#\\{\\s*random\\s*\\{(?<random>.*?)\\}\\}";
    private static final Pattern RANDOM_NUMBER_PATTERN_COMPILED = Pattern.compile(RANDOM_NUMBER_PATTERN);
    //    #{random{,10,mmAT01}}

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
        return (T) STASH.get(name);
    }


    public static <T> T evalVariable(String param) {
        if (param.trim().matches(".*" + RANDOM_NUMBER_PATTERN + ".*")) {
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