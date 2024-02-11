package tag24.objects

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import tag24.drivers.ExecutionWebDriver


/**
 * Class responsible for interacting with the location-chooser.
 */
class LocationChooseWindow {
    private val relativeXpath: WebElement

    init {
        val driver: WebDriver = ExecutionWebDriver.getConfiguredWebDriver()

        // Attempt to find the location-selector by its id. If not found, throw an exception.
        relativeXpath = driver.findElement(By.id("location-select"))
            ?: throw NoSuchElementException("Modal box not found.")

        // Ensure that the modal box is displayed on the page, otherwise throw an exception.
        check(relativeXpath.isDisplayed) { "Modal box is not displayed." }
    }

    /**
     * Chooses a location within the location-chooser by clicking on the list item that matches the provided location string.
     * @param location The name of the location to select.
     * @throws NoSuchElementException if the location is not found within the list.
     */
    fun chooseLocation(location: String) {
        // Find all list item elements (li tags) within the modal box.
        val headlines = relativeXpath.findElements(By.tagName("li"))

        // Find the list item that matches the provided location string.
        val locationElement = headlines.find { it.text == location }
            ?: throw NoSuchElementException("Location '$location' not found in the navigation window.")

        locationElement.click()
    }
}
