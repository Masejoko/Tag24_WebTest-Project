package tag24.objects

import tag24.drivers.ExecutionWebDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * Handles interactions with the cookie consent popup.
 */
class CookiePopUp {
    private var relativeXpath: WebElement

    /**
     * Initializes the CookiePopUp by finding the iframe containing the consent message
     * and switching to it to interact with the consent elements.
     */
    init {
        val driver: WebDriver = ExecutionWebDriver.getConfiguredWebDriver()
        // find desired iframe by tag-name and display status
        val iframe = driver.findElements(By.tagName("iframe"))
            .find { it.isDisplayed }

        // switch to iframe and find cookie consent popup
        if (iframe != null) {
            driver.switchTo().frame(iframe)
            relativeXpath = driver.findElement(By.id("notice"))
        } else {
            throw NoSuchElementException("Displayed iframe not found")
        }
    }

    /**
     * Clicks the button to confirm cookies based on its title.
     */
    fun confirmCookies(){
        val consentButton = relativeXpath.findElement(By.xpath(".//button[contains(@title,'Zustimmen')]"))
        consentButton.click()
    }

}