import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class SimpleParser<T> {
    private static final Gson gson = new GsonBuilder().create();
    private final Class<T> className;

    public SimpleParser(Class<T> className) {
        this.className = className;
    }

    public List<T> parseCSV(String[] columnMapping, String fileName) {
        List<T> resList = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(className);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<T> csv = new CsvToBeanBuilder<T>(reader).withMappingStrategy(strategy).build();
            resList = csv.parse();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resList;
    }

    public String listToJson(List<T> list) {
        Type listType = new TypeToken<List<T>>() {
        }.getType();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(list, listType);
    }

    public void writeToFile(String fileName, String text) {
        try (FileWriter writer = new FileWriter(fileName); BufferedWriter bufWriter = new BufferedWriter(writer)) {
            bufWriter.write(text);
            bufWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readString(String fileName) {
        StringBuilder resultString = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                resultString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString.toString();
    }

    public List<T> jsonToList(String json) {
        List<T> list = gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }
}
