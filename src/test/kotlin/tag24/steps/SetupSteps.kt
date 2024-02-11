package tag24.steps

import io.cucumber.java.en.Given
import io.cucumber.java.en.And
import org.openqa.selenium.WebDriverException
import org.slf4j.LoggerFactory
import tag24.objects.CookiePopUp
import tag24.util.threadTimeout


class SetupSteps{

    private val LOG = LoggerFactory.getLogger(SetupSteps::class.java)

    @Given("I open the browser to tag24 website")
    fun navigateToUrl(){
        //since our Hooks launch the browser, we just wait -> may change with further implementation
        threadTimeout(3000)
    }

    @And("I accept the cookies")
    fun acceptCookies() {
        try {
            CookiePopUp().confirmCookies()
            LOG.info("Cookies accepted.")
        } catch (e: WebDriverException) {
            LOG.error("Error interacting with the cookie popup.", e)
        } catch (e: NoSuchElementException) {
            LOG.info("Cookie popup not found, possibly already accepted.")
        }
    }
}