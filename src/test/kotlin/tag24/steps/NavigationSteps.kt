package tag24.steps


import io.cucumber.java.en.And
import org.slf4j.LoggerFactory
import org.testng.Assert
import tag24.objects.LocationChooseWindow
import tag24.objects.Page


class NavigationSteps {
    private lateinit var location : String
    private val LOG = LoggerFactory.getLogger(NavigationSteps::class.java)

    @And("^I choose the location (.*)$")
    fun chooseLocation(location: String){
        try {
            val locationChooser = LocationChooseWindow()
            locationChooser.chooseLocation(location)
        } catch (e: Exception){
            LOG.info("Location was already chosen using the Navigation Menu")
            val page = Page()
            page.clickLocationSelectorButton()

            val locationChooser = LocationChooseWindow()
            locationChooser.chooseLocation(location)
        }
        this.location = location
    }

    @And("I confirm the location")
    fun confirmLocation(){
        // could be used for further testing
//        var url = WebUtil.getCurrentUrl().split("/").last()
        var locationSelected = Page().getLocationSelected()
        Assert.assertTrue(locationSelected.equals(location, ignoreCase = true),
            "Expected location '$location' but found '$locationSelected'")
    }
}