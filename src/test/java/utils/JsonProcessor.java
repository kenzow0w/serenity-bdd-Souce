package utils;

import com.jayway.jsonpath.JsonPath;
import exceptions.AutotestError;
import lombok.SneakyThrows;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import static utils.Evaluator.setVariable;

public class JsonProcessor {

    private static File jsonFolder;

    static {
        jsonFolder = new File(System.getProperty("user.dir") + "/src/test/resources/json");
    }

    @SneakyThrows
    public static Object getJsonDocument(String jsonFileName) {
        Object obj;
        File file = null;
        try {
            Path jsonFile = findFile(jsonFileName);
            file = new File(String.valueOf(jsonFile));
            JSONParser parser = new JSONParser();
            obj = parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8")));
        } catch (ParseException e) {
            throw new AutotestError(String.format("Не удалось распарсить Json файл {%s}", jsonFileName));
        } catch (FileNotFoundException e) {
            throw new AutotestError(String.format("Не удалось прочитать файл {%s}", file));
        } catch (UnsupportedEncodingException e) {
            throw new AutotestError(String.format("Данная кодировка символов не поддерживается.Необходимая кодировка UTF-8. Проверьте кодировку JSON файла", file));
        }
        return obj;
    }

    @SneakyThrows
    private static Path findFile(String fileName) {
        if (!jsonFolder.exists()) {
            throw new AutotestError(String.format("Catalog %s doesn't exist", jsonFolder.getAbsolutePath()));
        }
        Path path;
        try {
            path = Files.walk(Paths.get(jsonFolder.toURI())).filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString().equals(fileName)).findFirst()
                    .orElseThrow(() -> new AutotestError(String.format("In folder %s doesn't find file %s", jsonFolder, fileName)));
        } catch (IOException e) {
            throw new AutotestError(String.format("Error in opening file {%s}", fileName));
        }
        return path;
    }

    public static void setValueByJsonPath(String JSONVar, String jsonPath, String value) {
        String jsonString = JsonPath.parse((String) Evaluator.getVariable(String.format("#{%s}", JSONVar))).set(jsonPath, value).jsonString();
        setVariable(JSONVar, jsonString);
    }


}
