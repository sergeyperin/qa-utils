
import com.gargoylesoftware.htmlunit.ThreadedRefreshHandler
import geb.report.ReportState
import geb.report.Reporter
import geb.report.ReportingListener
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import shared.Common


if (System.getProperty("test.env") == null) {
  test_environment = "local"
} else {
  test_environment = System.getProperty("test.env")
}


def config = Common.loadConfig();

baseUrl = config["application_portal"]

baseNavigatorWaiting = false

reportingListener = new ReportingListener() {
  void onReport(Reporter reporter, ReportState reportState, List<File> reportFiles) {
    reportFiles.each {
      println "[[ATTACHMENT|$it.absolutePath]]"
    }
  }
}

environments {

  chrome {
    ChromeOptions options = new ChromeOptions()
    Map<String, Object> prefs = new HashMap<String, Object>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    options.setExperimentalOption("prefs", prefs);
    options.addArguments("use-fake-device-for-media-stream")
    options.addArguments("use-fake-ui-for-media-stream")
    options.addArguments("--disable-notifications")
    options.addArguments("--lang=es");
    options.addArguments("--disable-confirmation")
    options.addArguments("disable-infobars")
    driver = { new ChromeDriver(options) }
  }

  chromeLocalHeadless {
    ChromeOptions options = new ChromeOptions()
    Map<String, Object> prefs = new HashMap<String, Object>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    options.setExperimentalOption("prefs", prefs);
    options.addArguments("no-sandbox")
    options.addArguments("use-fake-device-for-media-stream")
    options.addArguments("use-fake-ui-for-media-stream")
    options.addArguments("disable-notifications")
    options.addArguments("disable-confirmation")
    options.addArguments("disable-infobars")
    options.addArguments("window-size=1600,1200")
    DesiredCapabilities capabilities = DesiredCapabilities.chrome()
    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    driver = {
      new RemoteWebDriver(
              new URL("http://127.0.0.1:4444/wd/hub"), capabilities
      )
    }
  }
}

waiting {
  includeCauseInMessage = true
  timeout = 10
  retryInterval = 0.5
  presets {
    slow {
      timeout = 20
      retryInterval = 1
    }
    quick {
      timeout = 5
      retryInterval = 0.25
    }
  }
}