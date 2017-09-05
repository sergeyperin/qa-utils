import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber.class)
@CucumberOptions(plugin = ['pretty', 'junit:build/cucumber.xml', 'html:target/cucumber-html-report'],
        glue = ['src/test/groovy/pages', 'src/test/groovy/steps', 'classpath:io.jdev.geb.cucumber.steps.groovy.en'],
        tags = ['~@defect', '~@updateTest'],
        features = ['src/test/resources/features']
)
class RunWebUiCukeTests {
}
