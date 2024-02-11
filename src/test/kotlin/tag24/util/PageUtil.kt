package tag24.util

import org.apache.commons.io.FileUtils
import org.openqa.selenium.*
import org.slf4j.LoggerFactory
import tag24.drivers.ExecutionWebDriver
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility object for common page interactions, such as taking screenshots.
 */
object PageUtil {

    private val LOG = LoggerFactory.getLogger(PageUtil::class.java)

    /**
     * Takes a screenshot of the current page and stores it in the specified directory.
     *
     * @param testCaseId Identifier of the test case calling the method, used to
     *                   name the screenshot file.
     * @return The file reference to the saved screenshot.
     */
    fun takeScreenshot(testCaseId: String): File {
        // Construct the path where screenshots will be saved
        val path = Paths.get("target", "screenshots").toString()

        // Generate a timestamped file name based on the testCaseId
        val now = Date()
        val fileName = "$testCaseId${SimpleDateFormat("yyyy-MM-dd_HH.mm.ss", Locale.ENGLISH).format(now)}.png"

        // Get the WebDriver instance and take a screenshot.
        val driver = ExecutionWebDriver.getConfiguredWebDriver()
        val img = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE)
        val savedFile = File(path, fileName)

        try {
            FileUtils.copyFile(img, savedFile)
            LOG.info("Screenshot saved at: ${savedFile.absolutePath}")
        } catch (e: IOException) {
            LOG.error("Caught Exception - indicating that the screenshot could not be saved. ${e.message}")
        }
        return savedFile
    }
}



