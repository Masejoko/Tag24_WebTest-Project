package tag24.runners

import io.cucumber.testng.AbstractTestNGCucumberTests
import io.cucumber.testng.CucumberOptions

@CucumberOptions(
    features = ["src/test/resources/features/"],
    glue = ["tag24.steps"],
    plugin = ["pretty", "json:target/cucumber-reports/Cucumber.json", "html:target/cucumber-reports.html"]
)
/**
 * Standard TestNGRunner for Cucumber used to execute test via commandline.
 * General approach is to add Tags or id via the commandline to specify the Test/s to be executed.
 */
class TestNGRunner : AbstractTestNGCucumberTests(){}
