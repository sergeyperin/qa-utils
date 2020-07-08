package model;

import helper.Const;
import helper.PropertyHelper;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
/**
 * View for BusinessData which are handled by BusinessDataHandler
 */
public class BusinessDataView {
    private PropertyHelper propertyHelper;

    public BusinessDataView(PropertyHelper propertyHelper) {
        this.propertyHelper = propertyHelper;
    }

    /**
     * Build xml.output as String from BusinessData object.
     * Doing validation for not supported properties. Not supported properties are mapped as null to BusinessData object
     */
    private void businessDataToXml() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        StringBuilder xmlOutput = new StringBuilder();
        BusinessData businessData = getBusinessData();

        assert new File(Const.INPUT_FILE).exists(): "Failed to substitute variables due to input.xml template is not found";

        for (String line : readXml(Paths.get(Const.INPUT_FILE))) {
            for (PropertyTypeEnum p : PropertyTypeEnum.values()) {
                String substituteFormatProperty = "${" + p.name() + "}";
                if (line.contains(substituteFormatProperty)) {
                    String value = p.getData(businessData);
                    // validation: empty fields in model should not be substituted
                    if (value == null || value.isEmpty()) {
                        line = "";
                    } else {
                        line = line.replace(substituteFormatProperty, value);
                    }
                }
            }
            xmlOutput.append(line).append("\n");
        }
        // to get rid of empty lines
        String xmlOutputNoEmptyLines = xmlOutput.toString().replaceAll("(?m)^\\s", "");
        stringToDom(xmlOutputNoEmptyLines);
    }

    /**
     * Writes xml.output to file system
     *
     * to be on the safe side and overcome encoding issues - it parse the source, transform and build it.
     * Good practice to never trust your inputs, this might be the preferable way.
     * @return BusinessData
     * @throws FileNotFoundException, if properties file is absent
     */
    private void stringToDom(String xmlSource) throws SAXException, ParserConfigurationException, IOException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));
        // Use a Transformer for output
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(Const.OUTPUT_FILE));
        transformer.transform(source, result);
    }

    /**
     * Returns BusinessData object, parsed from properties file.
     * @return BusinessData
     * @throws FileNotFoundException, if properties file is absent
     */
    private BusinessData getBusinessData() throws FileNotFoundException {
        BusinessDataHandler businessDataHandler = new BusinessDataHandler(BusinessData.newBuilder());
        Set<String> allProps = this.propertyHelper.getAllPropertyNames();
        if (allProps == null || allProps.isEmpty())
            throw new FileNotFoundException(this.propertyHelper.getPropertiesPath() + " isn't found on filesystem");
        String propValue = null;
        for (String propName : allProps) {
            propValue = this.propertyHelper.getProperty(propName);
            businessDataHandler.mapPropertyToModel(propName, propValue);
        }
        return businessDataHandler.build();
    }

    private static List<String> readXml(Path filePath) throws IOException {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return lines;
    }

    public List<String> getOutput() throws IOException, TransformerException, SAXException, ParserConfigurationException {
        businessDataToXml();
        return readXml(Paths.get(Const.OUTPUT_FILE));
    }

    static List<String> getInput() throws IOException {
        return readXml(Paths.get(Const.INPUT_FILE));
    }

    String getProperties() throws FileNotFoundException {
        return getBusinessData().toString();
    }

    public void printProperties() throws FileNotFoundException {
        System.out.println(getProperties());
    }

    public void printInput() throws IOException {
        getInput().forEach(t -> System.out.println(t));
    }

    public void printOutput() throws IOException, TransformerException, SAXException, ParserConfigurationException {
        getOutput().forEach(t -> System.out.println(t));
        // to clean the program result
        new File(Const.OUTPUT_FILE).delete();
    }

}
