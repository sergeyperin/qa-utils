import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

/**
 * Common shared methods for tests
 */
public class Helper {

    static List compareXML(Reader source, Reader target) throws SAXException, IOException {
        //creating Diff instance to compare two XML files

        Diff xmlDiff = new Diff(source, target);
        //for getting detailed differences between two xml files
        DetailedDiff detailXmlDiff = new DetailedDiff(xmlDiff);
        return detailXmlDiff.getAllDifferences();
    }

    static void printDifferences(List differences) {
        int totalDifferences = differences.size();
        System.out.println("===============================");
        System.out.println("Total differences : " + totalDifferences);
        System.out.println("================================");
        for (Object difference : differences) {
            System.out.println(difference);
        }
    }

    static List getDifference(String actualFileName, String expectedFileName) throws IOException, SAXException {
        FileInputStream actual = new FileInputStream(actualFileName);
        FileInputStream expected = new FileInputStream(expectedFileName);
        BufferedReader actualSource = new BufferedReader(new InputStreamReader(actual));
        BufferedReader expectedSource = new BufferedReader(new InputStreamReader(expected));

        XMLUnit.setIgnoreWhitespace(true);

        return compareXML(actualSource, expectedSource);
    }

    static void assurePropertySubstitutedAtAll(List<String> output, String templatePropertyName) {
        boolean isOutputWithTimeStamp = output.stream().anyMatch(it -> it.contains("</" + templatePropertyName + ">") && it.contains("<" + templatePropertyName + ">"));
        assert isOutputWithTimeStamp : "Failed as" + templatePropertyName + "variable value is not substituted at all";
    }
}
