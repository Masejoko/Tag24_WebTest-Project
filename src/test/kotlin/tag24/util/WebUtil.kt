package tag24.util

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import tag24.drivers.ExecutionWebDriver
import java.util.concurrent.TimeUnit

/**
 * Function to get the Xpath of the given WebElement.
 * <a href="https://stackoverflow.com/questions/47069382/want-to-retrieve-xpath-of-given-webelement">...</a>
 * @param element: WebElement to receive the Xpath.
 * @return Xpath of the given WebElement as String
 */
fun getAbsoluteXPath(element: WebElement): String {
    val driver: WebDriver = ExecutionWebDriver.getConfiguredWebDriver()
    val js = driver as JavascriptExecutor
    return js.executeScript(
        """
        function absoluteXPath(element) {
            var comp, comps = [];
            var parent = null;
            var xpath = '';
            var getPos = function(element) {
                var position = 1, curNode;
                if (element.nodeType == Node.ATTRIBUTE_NODE) {
                    return null;
                }
                for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling) {
                    if (curNode.nodeName == element.nodeName) {
                        ++position;
                    }
                }
                return position;
            };

            if (element instanceof Document) {
                return '/';
            }

            for (; element && !(element instanceof Document); element = element.nodeType == Node.ATTRIBUTE_NODE ? element.ownerElement : element.parentNode) {
                comp = comps[comps.length] = {};
                switch (element.nodeType) {
                    case Node.TEXT_NODE:
                        comp.name = 'text()';
                        break;
                    case Node.ATTRIBUTE_NODE:
                        comp.name = '@' + element.nodeName;
                        break;
                    case Node.PROCESSING_INSTRUCTION_NODE:
                        comp.name = 'processing-instruction()';
                        break;
                    case Node.COMMENT_NODE:
                        comp.name = 'comment()';
                        break;
                    case Node.ELEMENT_NODE:
                        comp.name = element.nodeName;
                        break;
                }
                comp.position = getPos(element);
            }

            for (var i = comps.length - 1; i >= 0; i--) {
                comp = comps[i];
                xpath += '/' + comp.name.toLowerCase();
                if (comp.position !== null) {
                    xpath += '[' + comp.position + ']';
                }
            }

            return xpath;

        } return absoluteXPath(arguments[0]);
        """, element
    ) as String
}

/**
 * Function to highlight the given WebElement by adding a red border
 * May prove handy for debugging
 * @param element the WebElement to highlight
 */
fun highlightElement(element: WebElement) {
    val driver: WebDriver = ExecutionWebDriver.getConfiguredWebDriver()
    val jsExecutor = driver as JavascriptExecutor
    // give a red-border to the WebElement
    jsExecutor.executeScript("arguments[0].style.border='2px solid red'", element)
}

/** Method to tell the thread used to wait for a given amount of milliseconds.
 * @param ms milliseconds as int value.
 */
fun threadTimeout(ms: Int) {
    try {
        TimeUnit.MILLISECONDS.sleep(ms.toLong())
    } catch (e: InterruptedException) {
        throw RuntimeException(e)
    }
}

/**
 * Method to check if a WebElement is stale.
 * @param element WebElement that should be checked for stale state.
 * @return true if element is stale, otherwise false.
 */
fun isStale(element: WebElement): Boolean {
    return try {
        // Calling any method forces a staleness check
        element.isEnabled
        false
    } catch (expected: StaleElementReferenceException) {
        true
    }
}

/**
 * Method to remove Elements from a List if they are not in the list with all Elements.
 * @param completeList List containing all elements.
 * @param filteredList List containing elements after an update (e.g. filtering)
 * @return true if element is stale, otherwise false.
 */
fun removeNotExistingElements(completeList: List<String>, filteredList: MutableList<String>): List<String> {
    filteredList.removeIf { s -> !completeList.contains(s) }
    return filteredList
}

/**
 * Method to return current url of browser as String.
 */
fun getCurrentUrl(): String {
    val driver: WebDriver = ExecutionWebDriver.getConfiguredWebDriver()
    return driver.currentUrl
}
