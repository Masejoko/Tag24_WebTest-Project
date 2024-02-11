package tag24.objects

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.NoSuchElementException
import tag24.drivers.ExecutionWebDriver

/**
 * Represents the main page.
 */
class Page {
    private val driver: WebDriver = ExecutionWebDriver.getConfiguredWebDriver()
    private var locationSelector: WebElement

    // add further elements like the burger-menu etc.
    init {
        try {
            locationSelector = driver.findElement(By.className("header__channel")).findElement(By.xpath("*"))
        } catch (e: NoSuchElementException) {
            throw IllegalStateException("Location selector element not found on the page.", e)
        }
    }

    /**
     * Returns the text of the currently selected location.
     */
    fun getLocationSelected(): String {
        return locationSelector.text
    }

    /**
     * Simulates a click action on the location selector.
     */
    fun clickLocationSelectorButton() {
        try {
            locationSelector.click()
        } catch (e: Exception) {
            throw IllegalStateException("Failed to click on the location selector.", e)
        }
    }
}
