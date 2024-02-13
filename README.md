# Tag24 WebTest Project

## Description
This project utilizes Selenium and Cucumber for automated testing of the Tag24 website. It supports multiple browsers and employs the Page Object Model (POM) for maintainable test code. Designed for demonstration purposes.

### Important Notes
- **Demo Purpose**: Built for demonstration, the project opens the browser once for the session to better illustrate the testing flow, rather than opening and closing it for each test scenario.
- **Selectors**: Selenium selectors in this project are specific to demo targets and may need adjustments for more generic use or to adapt to changes on the website.
- **Browser Support**: While the project uses Chrome by default, users can switch to their desired web browser by modifying the `val BROWSER_NAME` in `drivers/ExecutionWebDriver`. Note that the test is only verified with Chromium-based browsers.
- **WebDriver Management**: The Maven plugin `bonigarcia/webdrivermanager` is used to automatically manage WebDriver binaries for different browsers. This ensures the latest versions of the WebDrivers are used, simplifying the setup process.

## Installation

### Prerequisites
- Java JDK (version 8 or above)
- Maven
- the latest version of Chrome (last tested with version 121)

### IntelliJ IDEA Plugins (Optional)
For using the Cucumber Runner within IntelliJ IDEA:
- Gherkin
- Cucumber for Java
- Cucumber for Groovy (optional, for Groovy step definitions)

### Setup
1. Clone the repository or download and extract the ZIP.
2. Install and configure Java and Maven on your system.
3. Ensure that Chrome is updated (open Chrome| Settings > about Google Chrome)

## Usage

### Running Tests
- **Via Maven**: Execute all tests with: "mvn test"
- **Via Cucumber Runner**:
 If using IntelliJ IDEA for the Cucumber Runner, ensure the required plugins are installed.
 Navigate to the runner class file and execute it directly from your IDE or command line.
- **Via TestNG**: To run tests via TestNG, execute the `testng.xml` file. This can be done through your IDE or by specifying it in Maven commands.

## Features
- Multi-browser support with easy switch between browsers in `drivers/ExecutionWebDriver`.
- Page Object Model (POM) for structured and maintainable code.
- Cucumber for readable, BDD-style test scenarios.
- TestNG for test organization and execution flexibility.
- Automatic WebDriver management using the bonigarcia WebDriverManager.

## Project Structure
- `src`: Source code, including test scenarios and step definitions.
- `drivers`: WebDriver management.
- `objects`: Page objects for the website.
- `runners`: Classes for executing tests.
- `steps`: Step definitions for Cucumber scenarios.
- `util`: Utility classes for common operations.
- `src/test/resources`: Location of the feature files.

