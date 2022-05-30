

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final EmployeeParser parser = new EmployeeParser();

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String csvFileName = "data.csv";
        String jsonFileName1 = "data.json";
        String jsonFileName2 = "dataTask2.json";
        String xmlFileName = "data.xml";
        //Задание 1
        List<Employee> list = parser.parseCSV(columnMapping, csvFileName);
        String json = parser.listToJson(list);
        parser.writeToFile(jsonFileName1, json);
        //Задание 2
        List<Employee> employeeList = parser.parseXML(xmlFileName);
        String json2 = parser.listToJson(employeeList);
        parser.writeToFile(jsonFileName2, json2);
        //Задание 3
        String json3 = parser.readString("data.json");
        List<Employee> employees =  parser.jsonToList(json3);
        System.out.println(employees);
    }
}
