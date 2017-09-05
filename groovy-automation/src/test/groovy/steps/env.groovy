package steps

import geb.Browser
import geb.binding.BindingUpdater
import org.openqa.selenium.OutputType
import org.openqa.selenium.WebDriverException
import org.slf4j.LoggerFactory
import shared.Common



import static cucumber.api.groovy.Hooks.Before
import static cucumber.api.groovy.Hooks.After

import io.github.bonigarcia.wdm.ChromeDriverManager
import io.github.bonigarcia.wdm.FirefoxDriverManager

def bindingUpdater
theBrowser = null
state = [:]




if (System.getProperty("geb.env").toLowerCase().contains("firefox")) {
  FirefoxDriverManager.getInstance().setup()
} else {
  try {
    ChromeDriverManager.getInstance().setup()
  } catch (RuntimeException e) {
    logger.warn("Unable to setup chrome driver. Will try again later (caused by multithreading).", e)
    def timeToWait = 60 * 1000
    sleep(timeToWait)
    ChromeDriverManager.getInstance().setup()
  }
}



Before() { scenario ->
  // actions for preparing AUT for test execution
  // for example:
  // account set-up, third party services initialization, etc.

  theBrowser = new Browser()
  bindingUpdater = new BindingUpdater(binding, new Browser())
  println("Starting WebDriver")
  bindingUpdater.initialize()
  println("Browser is up")
  try {
    theBrowser.driver.manage().window().maximize()
    println("Browser window maximized")
  } catch (Exception ex) {
    println("Failed to maximize browser, continuing")
  }
}

After() { scenario ->
  // actions for resetting AUT into initial state after test execution
  // for example:
  // account clean-up, close connection to database, delete application test rudiments, etc.

  bindingUpdater?.remove()
  // embed screenshot into cucumber report
  if (scenario.failed) {
    try {
      scenario.embed(theBrowser.driver.getScreenshotAs(OutputType.BYTES), "image/png")
    } catch (WebDriverException e) {
      // sometime firefox runs out of memory trying to take a screenshot, not a big deal so ignore
    } catch (MissingMethodException e) {
      // HTMLUnit doesn't support screenshots
    }
  }
}