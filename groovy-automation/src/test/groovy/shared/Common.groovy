package shared

import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.yaml.snakeyaml.Yaml


class Common {
  private static final Logger logger = LoggerFactory.getLogger(Common.class);
  private static final Common common = new Common();
  private static Map state;

  static Map getState() {
    return state;
  }

  public static final getInstance() {
    return common;
  }

  private Common() {
    state = new HashMap();
  }

  public void setSharedState(Map state) {
    this.state = state;
  }


  public static def loadConfig() {
    def environment;
    def config;

    if (System.getProperty("test.env") == null)
      environment = "local"
    else
      environment = System.getProperty("test.env")
    String fileContents = new File(System.getProperty("test.environmentConfig")).text
    Yaml yaml = new Yaml();
    config = yaml.load(fileContents)[environment]
    return config;
  }


}
