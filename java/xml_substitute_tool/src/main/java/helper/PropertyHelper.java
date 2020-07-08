package helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * PropertyHelper allows to parse properties file parameters as sets of param
 * Also can identify whether value is dynamic and do validation for supported properties
 */
public class PropertyHelper {

    private final Properties prop = new Properties();

    public String getPropertiesPath() {
        return propertiesPath;
    }

    protected String propertiesPath;

    /**
     * Returns PropertyHelper object.
     *
     * @propertyPath  path to properties file
     * @throws          IllegalArgumentException, if file format is different from .properties
     */
    public PropertyHelper(String propertyPath) {
        this.propertiesPath = propertyPath;
        InputStream input = null;

        try {
            if (!propertiesPath.endsWith(".properties")) {
                throw new IllegalArgumentException("Invalid file format. Please, use .properties file");
            }
            input = new FileInputStream(propertiesPath);
            prop.load(input);

        } catch (IOException ex) {
            //ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Returns property value by property name
     * @propertyPath  property key
     */
    public String getProperty(String key) {
        return prop.getProperty(key);
    }

    /**
     * Returns Set<String> properties names
     * @return        Set<String> properties names
     */
    public Set<String> getAllPropertyNames() {
        return prop.stringPropertyNames();
    }

    /**
     * Checks whether property is valid
     * "PROP" value means to be valid, PROP isn't valid
     * @param   validPropertyName is supported property name
     * @param   fromPropertyFile is input from properties file, should be validated
     * @return  bool, true if valid, otherwise is false.
     */
    public static boolean isValidProperty(String validPropertyName, String fromPropertyFile) {
        return Pattern.matches("\"" + validPropertyName + "\"", fromPropertyFile);
    }

    /**
     * Checks whether property should be programmatically changed.
     *  <PROP> value means to be changed programmatically
     * @param   input
     * @return  bool, true if should be programmatically changed, otherwise is false.
     */
    public static boolean isDynamicValue(String input) {
        return Pattern.matches("\"<.+>\"", input);
    }

}
