package tag24.steps

import io.cucumber.java.*
import org.openqa.selenium.WebDriver

import org.slf4j.LoggerFactory
import tag24.drivers.ExecutionWebDriver
import tag24.util.PageUtil
import java.io.IOException
import java.nio.file.Files
import java.time.Duration

// Global WebDriver instance, possibly move into class for parallel execution
lateinit var driver: WebDriver

// Logger for logging information and errors.
private val LOG = LoggerFactory.getLogger("Hooks")

/**
 * Sets up the WebDriver globally before any tests are run.
 * This function will be called once per test session.
 */
@BeforeAll
fun globalSetup() {
    try {
        driver = ExecutionWebDriver.getConfiguredWebDriver().apply {
            manage().timeouts().implicitlyWait(Duration.ofSeconds(2))
            manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10))
            manage().window().maximize()
        }
        if (!ExecutionWebDriver.browserStarted) driver.get("https://www.tag24.de")
        LOG.info("Set up a new WebDriver: $driver")
    } catch (e: Exception) {
        LOG.error("Could not open https://www.tag24.de. Server online? VPN activated? ", e)
        throw e
    }
}

/**
 * Closes Browser after all tests are done.
 */
@AfterAll
fun globalTeardown() {
    try {
        ExecutionWebDriver.closeDriver()
        LOG.info("WebDriver closed")
    } catch (e: Exception) {
        LOG.error("Error closing WebDriver", e)
    }
}

/**
 * The Hooks class contains methods annotated with Cucumber's Before and After annotations,
 * which are executed before and after each scenario, respectively.
 */
class Hooks {
    /**
     * Notifies if driver is loaded correctly.
     */
    @Before
    fun setupTest() {
        LOG.info("Before Test - Current WebDriver: $driver")
    }

    /**
     * Teardown after each test.
     * Takes a screenshot if the test fails.
     *
     * @param scenario The current Cucumber scenario.
     */
    @After
    fun tearDownTest(scenario: Scenario) {
        if (scenario.isFailed) {
            try {
                val screenshot = PageUtil.takeScreenshot("Feature_${scenario.name}")
                Files.readAllBytes(screenshot.toPath()).also { screenshotBytes ->
                    scenario.attach(screenshotBytes, "image/png", "Feature_${scenario.name}")
                }
                LOG.info("Screenshot taken for failed scenario: ${scenario.name}")
            } catch (e: IOException) {
                LOG.error("Failed to take screenshot for scenario: ${scenario.name}", e)
            }
        }
    }
}
