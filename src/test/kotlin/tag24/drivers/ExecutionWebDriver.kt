package tag24.drivers

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.devtools.DevTools
import org.openqa.selenium.devtools.v120.network.Network
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.safari.SafariDriver
import org.openqa.selenium.WebDriverException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.devtools.v120.network.Network.enable
import org.openqa.selenium.devtools.v120.network.model.ConnectionType
import tag24.config.SupportedBrowsers
import java.util.*

/**
 * ExecutionWebDriver is a utility class that provides a singleton instance of WebDriver.
 * It is designed to initialize and manage the lifecycle of the WebDriver instance based on the specified browser type.
 * This class supports multiple browsers (Chrome, Firefox, Edge, Safari) and includes features for debugging,
 * setting network conditions, and handling browser-specific DevTools.
 */
class ExecutionWebDriver private constructor() {
    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ExecutionWebDriver::class.java)

        // could be moved to a config file
        private val BROWSER_NAME = SupportedBrowsers.CHROME
        //in debug the local Webdriver is used and the Browser will not close after the Session
        private const val debug = false

        private var driver: WebDriver? = null
        private var devTools: DevTools? = null
        var browserStarted = false

        // Method to get the configured WebDriver instance
        fun getConfiguredWebDriver(): WebDriver {
            synchronized(this) {
                if (driver == null) {
                    LOG.info("Using browser: $BROWSER_NAME")

                    // Initialize the WebDriver based on the specified browser
                    driver = when (BROWSER_NAME) {
                        SupportedBrowsers.FIREFOX -> setupFirefoxDriver()
                        SupportedBrowsers.CHROME -> setupChromeDriver(debug)
                        SupportedBrowsers.EDGE -> setupEdgeDriver()
                        SupportedBrowsers.SAFARI -> SafariDriver()
                        else -> {
                            LOG.error("Unsupported browser: $BROWSER_NAME. Not configured?")
                            throw WebDriverException("Unsupported browser: $BROWSER_NAME")
                        }
                    }
                }
                return driver ?: throw IllegalStateException("Driver not initialized")
            }
        }

        // Setup method for FirefoxDriver
        private fun setupFirefoxDriver(): WebDriver {
            WebDriverManager.firefoxdriver().setup()
            return FirefoxDriver().also { devTools = it.devTools.apply { createSession() } }
        }

        // Setup method for ChromeDriver with optional debug configuration
        private fun setupChromeDriver(debug: Boolean): WebDriver {
            if (debug) {
                return try {
                    // Attempt to set up ChromeDriver for debugging
                    val rootDir = System.getProperty("user.dir")
                    System.setProperty("webdriver.chrome.driver", "$rootDir/chromedriver.exe")
                    val options = ChromeOptions()
                    options.setExperimentalOption("debuggerAddress", "localhost:1337")
                    ChromeDriver(options).also { browserStarted = true }
                } catch (e: Exception) {
                    // Case where Browser is already opened
                    val rootDir = System.getProperty("user.dir")
                    System.setProperty("webdriver.chrome.driver", "$rootDir/chromedriver.exe")
                    val options = ChromeOptions()
                    options.addArguments("--remote-debugging-port=1337")
                    ChromeDriver(options)
                }
            } else {
                // Standard setup for ChromeDriver without debug options
                WebDriverManager.chromedriver().setup()
                return ChromeDriver().also { devTools = it.devTools.apply { createSession() } }
            }
        }

        // Setup method for EdgeDriver
        private fun setupEdgeDriver(): WebDriver {
            WebDriverManager.edgedriver().setup()
            return EdgeDriver().also { devTools = it.devTools.apply { createSession() } }
        }

        // Method to close and clean up the WebDriver instance
        fun closeDriver() {
            LOG.info("Closing webdriver: $driver")
            driver?.quit()
            driver = null
        }

        // Method to set the network conditions to offline
        fun setNetworkOffline() {
            if (driver !is SafariDriver) {
                devTools?.send(enable(Optional.empty(), Optional.empty(), Optional.empty()))
                devTools?.send(Network.emulateNetworkConditions(true, 200, 10000, 200000, Optional.of(ConnectionType.ETHERNET)))
            }
        }

        // Method to reset the network conditions to online
        fun setNetworkOnline() {
            if (driver !is SafariDriver) {
                devTools?.send(Network.disable())
            }
        }
    }

    // Prevent instantiation of this utility class
    init {
        throw UnsupportedOperationException("Utility classes cannot be instantiated")
    }
}

