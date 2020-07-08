package helper;

import java.io.File;

public class Const {

    // paths
    public static final String USER_DIR = System.getProperty("user.dir");
    private static final String RESOURCES_DIR = USER_DIR + File.separator + "src" + File.separator + "main" + File.separator + "resources";

    // names
    public static final String PROPERTY_FILE = RESOURCES_DIR + File.separator + "example.properties";
    public static final String INPUT_FILE = RESOURCES_DIR + File.separator + "input.xml";
    public static final String OUTPUT_FILE = RESOURCES_DIR + File.separator + "output.xml";

    // cmd options
    public static final String HELP = "h";
    public static final String INPUT = "i";
    public static final String OUTPUT = "o";
    public static final String PROPERTIES = "p";

    //help
    public static final String PROJECT_PURPOSE = "Parse variables from properties file and substitute by variables in xml template, producing output xml document";
    public static final String USAGE = "java -jar ./build/libs/tech.task.perin-1.0-SNAPSHOT.jar -key\n";


}
