import helper.PropertyCustomPathHelper;
import model.BusinessDataView;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestsNegative extends TestBase {

    @Test(expected = FileNotFoundException.class)
    public void shouldNotSubstituteAnythingAndBuildOutputWhenEmptyPropertiesFile() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        String propertiesFileName = propertyStoragePath + "example_empty_properties_file.properties";
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        businessDataView.getOutput();
    }

    @Test
    public void propertyWithoutValueShouldNotBeSubstitutedInOutput() throws SAXException, TransformerException, ParserConfigurationException, IOException {

        // pre conditions
        String propertiesFileName = propertyStoragePath + "example_property_without_value.properties";
        String expectedFileName = propertyStoragePath + "example_property_without_value.xml";

        // steps
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        businessDataView.getOutput();

        // verification
        List differences = Helper.getDifference(super.actualFileName, expectedFileName);
        if (!differences.isEmpty()) {
            Helper.printDifferences(differences);
        }
        assertTrue("Failed because property without value is substituted by xml template", differences.isEmpty());


    }


    @Test(expected = FileNotFoundException.class)
    public void shouldNotBuildOutputWhenPropertiesFileNotExists() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        String propertiesFileName = propertyStoragePath + "example_not_existing.properties";
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        businessDataView.getOutput();
    }

    @Test
    public void shouldNotIncludeNotSupportedPropertiesToOutput() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        // pre conditions
        String propertiesFileName = propertyStoragePath + "example_not_supported_properties.properties";
        String expectedFileName = propertyStoragePath + "example_not_supported_properties.xml";

        // steps
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        businessDataView.getOutput();

        // verification
        List differences = Helper.getDifference(super.actualFileName, expectedFileName);
        if (!differences.isEmpty()) {
            Helper.printDifferences(differences);
        }
        assertTrue("Failed, not supported property is substituted by xml template", differences.isEmpty());
    }

    @Test
    public void onlyLastPropertyShouldBeSubstitutedIfItIsDuplicated() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        // pre conditions
        String propertiesFileName = propertyStoragePath + "example_duplicated_properties.properties";
        String expectedFileName = propertyStoragePath + "example_duplicated_properties.xml";

        // steps
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        businessDataView.getOutput();

        // verification
        List differences = Helper.getDifference(super.actualFileName, expectedFileName);
        if (!differences.isEmpty()) {
            Helper.printDifferences(differences);
        }
        assertTrue("Failed because duplicated properties with original ones are substituted by xml template", differences.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBuildOutputWhenFileWithVariablesIsNotPropertiesFile() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        // pre conditions
        String propertiesFileName = propertyStoragePath + "properties.xml";

        // steps
        BusinessDataView businessDataView = new BusinessDataView(new PropertyCustomPathHelper(propertiesFileName));
        businessDataView.getOutput();

    }

}
