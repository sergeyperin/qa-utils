import helper.PropertyCustomPathHelper;
import model.BusinessDataView;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class TestsPositive extends TestBase {


    @Test
    public void propertiesShouldBeSubstitutedInOutputXml() throws SAXException, TransformerException, ParserConfigurationException, IOException {

        // pre conditions
        String propertiesFileName = propertyStoragePath + "example_positive.properties";
        String expectedFileName = propertyStoragePath + "example_positive.xml";

        // steps
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        businessDataView.getOutput();

        // verification
        List differences = Helper.getDifference(super.actualFileName, expectedFileName);
        if (!differences.isEmpty()) {
            Helper.printDifferences(differences);
        }
        assertTrue("Failed to substitute values by xml template", differences.isEmpty());


    }

    @Test
    public void onePropertyShouldBeSubstitutedInOutput() throws SAXException, TransformerException, ParserConfigurationException, IOException {

        // pre conditions
        String propertiesFileName = propertyStoragePath + "example_one_property.properties";
        String expectedFileName = propertyStoragePath + "example_one_property.xml";

        // steps
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        businessDataView.getOutput();

        // verification
        List differences = Helper.getDifference(super.actualFileName, expectedFileName);
        if (!differences.isEmpty()) {
            Helper.printDifferences(differences);
        }
        assertTrue("Failed to substitute property with one value only by xml template", differences.isEmpty());

    }

    @Test
    public void propertyWithDynamicPlaceholderValueShouldBeReplaceProgramaticallyAndSubstitutedInOutput() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        // pre conditions
        String propertiesFileName = propertyStoragePath + "example_dynamic_property.properties";
        String expectedFileName = propertyStoragePath + "example_dynamic_property.xml";

        // steps
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        businessDataView.getOutput();

        // verification
        List differences = Helper.getDifference(super.actualFileName, expectedFileName);
        if (!differences.isEmpty()) {
            Helper.printDifferences(differences);
        }
        assertFalse("Failed to substitute property with dynamic value placeholder by xml template", differences.isEmpty());

    }

    @Test
    public void timeStampPropertyShouldBeSubstitutedAsCurrentDateTimeInOutput() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        // pre conditions
        String propertiesFileName = propertyStoragePath + "example_dynamic_property.properties";
        final Pattern DATETIME_REGEX = Pattern.compile("\\d{4}\\.\\d{2}\\.\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        List<String> output = businessDataView.getOutput();
        String parsedDateTime = null;

        Helper.assurePropertySubstitutedAtAll(output, "TIMESTAMP");

        // actions
        for (String line : output) {
            final Matcher matcherDateTime = DATETIME_REGEX.matcher(line);
            if (line.contains("TIMESTAMP")) {
                if (matcherDateTime.find()) {

                    parsedDateTime = matcherDateTime.group();

                    SimpleDateFormat expectedFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                    Date expectedDate = null;
                    try {
                        expectedDate = expectedFormat.parse(parsedDateTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // verification
                    assertNotNull("Failed to substitute TIMESTAMP property in date time value format", expectedDate);
                }
            }
        }
    }

    @Test
    public void uuidPropertyShouldBeSubstitutedAsUniversallyUniqueIdentifierInOutput() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        // pre conditions
        String propertiesFileName = propertyStoragePath + "example_uuid_property.properties";
        final Pattern UUID_REGEX = Pattern.compile("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b");
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        List<String> output = businessDataView.getOutput();
        String parsedUUID = null;
        UUID expectedUUID = null;

        Helper.assurePropertySubstitutedAtAll(output, "UUID");

        // actions
        for (String line : output) {
            final Matcher matcherUUID = UUID_REGEX.matcher(line);
            if (line.contains("UUID")) {
                if (matcherUUID.find()) {
                    parsedUUID = matcherUUID.group();
                    try {
                        expectedUUID = UUID.fromString(parsedUUID);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    //verification
                    assertNotNull("Failed to substitute UUID property with universally unique identifier format", expectedUUID);
                }
            }
        }

    }

    @Test
    public void requestIdPropertyShouldBeSubstitutedAsRandomIntegerInOutput() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        // pre conditions
        String propertiesFileName = propertyStoragePath + "example_requestid_property.properties";
        final Pattern INTEGER_REGEX = Pattern.compile("^-?\\d+$");
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        List<String> output = businessDataView.getOutput();
        String parsedInteger = null;
        Integer expectedInteger = null;

        Helper.assurePropertySubstitutedAtAll(output, "REQUEST_ID");

        // actions
        for (String line : output) {
            final Matcher matcherInteger = INTEGER_REGEX.matcher(line);
            if (line.contains("REQUEST_ID")) {
                if (matcherInteger.find()) {
                    parsedInteger = matcherInteger.group();
                    try {
                        expectedInteger = Integer.valueOf(parsedInteger);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //verification
                    assertNotNull("Failed to substitute REQUEST_ID property with random integer format", expectedInteger);
                }
            }
        }


    }


}
