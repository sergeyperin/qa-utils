import helper.Const;
import org.junit.After;
import org.junit.BeforeClass;

import java.io.File;

public class TestBase {

    protected static String propertyStoragePath = null;
    protected final String actualFileName = Const.OUTPUT_FILE;


    @BeforeClass
    public static void before() {
        // property storage location is initialized before test class, as it is relevant for all tests
        TestBase.propertyStoragePath = Const.USER_DIR + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;
    }

    @After
    public void after() {
        // delete xml output after each test
        File outputXml = new File(Const.OUTPUT_FILE);
        if (outputXml.exists()) {
            outputXml.delete();
        }
    }
}
