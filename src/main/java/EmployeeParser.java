import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmployeeParser {

    private static final SimpleParser<Employee> simpleParser = new SimpleParser<>(Employee.class);

    public List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));
        Node root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        List<Employee> employeeList = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item).filter(i -> Node.ELEMENT_NODE == i.getNodeType()).map(i -> (Element) i).map(i -> {
            long id = Long.parseLong(i.getElementsByTagName("id").item(0).getTextContent());
            String firstName = i.getElementsByTagName("firstName").item(0).getTextContent();
            String lastName = i.getElementsByTagName("lastName").item(0).getTextContent();
            String country = i.getElementsByTagName("country").item(0).getTextContent();
            int age = Integer.parseInt(i.getElementsByTagName("age").item(0).getTextContent());
            return new Employee(id, firstName, lastName, country, age);
        }).collect(Collectors.toList());
        return employeeList;
    }

    public List<Employee> parseCSV(String[] columnMapping, String fileName) {
        return simpleParser.parseCSV(columnMapping, fileName);
    }

    public String listToJson(List<Employee> list) {
        return simpleParser.listToJson(list);
    }

    public void writeToFile(String fileName, String text) {
        simpleParser.writeToFile(fileName, text);
    }

    public String readString(String fileName) {
        return simpleParser.readString(fileName);
    }

    public List<Employee> jsonToList(String json) {
        return simpleParser.jsonToList(json);
    }


}
