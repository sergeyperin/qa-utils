import helper.Const;
import helper.PropertyHelper;
import model.BusinessDataView;
import org.apache.commons.cli.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;


/**
 * Cli - command line interface
 */
public class Cli {

    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {

        BusinessDataView businessDataView = new BusinessDataView(new PropertyHelper(Const.PROPERTY_FILE));
        Options options = buildOptions();
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = null;

        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            notSupportedOption(options);
        }

        assert commandLine != null: "Failed to get cmdline";

        if (commandLine.hasOption(Const.HELP)) {
            helpOption(options);
        } else if (commandLine.hasOption(Const.PROPERTIES)) {
            businessDataView.printProperties();
            System.exit(0);
        } else if (commandLine.hasOption(Const.INPUT)) {
            businessDataView.printInput();
            System.exit(0);
        } else if (commandLine.hasOption(Const.OUTPUT)) {
            businessDataView.printOutput();
            System.exit(0);
        } else {
            notSupportedOption(options);
        }
    }

    private static void notSupportedOption(Options options) {
        System.out.println("Option is absent or not supported");
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Available keys:", options, true);
        System.exit(0);
    }

    private static void helpOption(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        System.out.println(Const.PROJECT_PURPOSE);
        formatter.printHelp(Const.USAGE + "\n" + "Available keys:", options, true);
        System.exit(0);
    }

    private static Options buildOptions() {
        Option optionHelp = new Option(Const.HELP, "help", false, "help");
        Option optionInput = new Option(Const.INPUT, "input", false, "shows input.xml template");
        Option optionOutput = new Option(Const.OUTPUT, "output", false, "produces output.xml from input.xml template");
        Option optionProperties = new Option(Const.PROPERTIES, "properties", false, "shows properties substituted in input.xml to be present in output.xml");
        return new Options().addOption(optionHelp).addOption(optionInput).addOption(optionOutput).addOption(optionProperties);
    }
}
