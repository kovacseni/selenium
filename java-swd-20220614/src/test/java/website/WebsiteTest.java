package website;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

// Tesztosztály
@Slf4j
@SeleniumTest
class WebsiteTest {

//    // Teszteset
//    @Test
//    void testSearch() {
//        // Given
//        WebDriverManager.chromedriver().setup();
//        WebDriver driver = new ChromeDriver();
//        driver.get("https:www.python.org");
//
//        // When
//        driver.findElement(By.id("id-search-field")).click();
//        driver.findElement(By.id("id-search-field")).sendKeys("testing");
//        driver.findElement(By.id("submit")).click();
//
//        // Then
//        String result = driver.findElement(By.cssSelector("h3:nth-child(2)")).getText();
//        assertEquals("Results", result);
//
//        driver.quit();
//    }

//    private static final Logger log = LoggerFactory.getLogger(WebsiteTest.class);

//    WebDriver driver;
//
//    @BeforeAll
//    static void initWebDriverManager() {
//        WebDriverManager.chromedriver().setup();
//    }
//
//    @BeforeEach
//    void initDriver() {
//        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
//        // Optionally remove existing handlers attached to j.u.l root logger
//        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)
//        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
//        // the initialization phase of your application
//        SLF4JBridgeHandler.install();
//        ChromeOptions options = new ChromeOptions();
//        // A felugró ablakok és a felső sor (automatizált szoftverek)o letiltása:
//        options.setExperimentalOption("excludeSwitches",
//                List.of("enable-automation", "disable-popup-blocking"));
//        driver = new ChromeDriver(options);
//        driver.get("https:www.python.org");
//    }
//
//    @AfterEach
//    void quitDriver() {
//        driver.quit();
//    }

    @Test
    void testSearch(WebDriver driver) {
        driver.get("https:www.python.org");
        driver.findElement(By.id("id-search-field")).click();
        driver.findElement(By.id("id-search-field")).sendKeys("testing");
        driver.findElement(By.id("submit")).click();
        log.debug("Click on GO button");

        String result = driver.findElement(By.cssSelector("h3:nth-child(2)")).getText();
        assertEquals("Results", result);
    }

    @Test
    void testPsf(WebDriver driver) {
        driver.get("https:www.python.org");
        driver.findElement(By.linkText("PSF")).click();
        log.debug("Click on pdf menu item");
        assertEquals("Python Software Foundation", driver.getTitle());
    }

    @Test
    void testClickButton(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/index.html");
        driver.findElement(By.cssSelector("#name-input")).sendKeys("John Doe");
        driver.findElement(By.cssSelector("#submit-button")).click();

        String result = driver.findElement(By.cssSelector("#message-div")).getText();
        assertEquals("Hello John Doe!", result);
    }

    @Test
    void testSetBorder(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/index.html");
        WebElement input = driver.findElement(By.id("field-to-validate"));
        String value = input.getText();
        if (Integer.parseInt(value) != 100) {
            WebElement parent = (WebElement) ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style['border'] = '3px solid red';", input
            );
        }
    }

    @Test
    void testGrid(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/grid/index.html");
        WebElement cell5 = driver.findElement(with(By.cssSelector("tr:nth-child(2) > td:nth-child(2)")));
        WebElement cell2 = driver.findElement(with(By.tagName("td")).below(cell5));

        assertEquals("2", cell2.getText());
    }

    @Test
    void testScreenShot(WebDriver driver) throws IOException {
        driver.get("http://127.0.0.1:5500/grid/index.html");
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.move(file.toPath(), Path.of("src/main/resources/screenshots/screenshot.png"), StandardCopyOption.REPLACE_EXISTING);

        File file2 = (driver.findElement(with(By.cssSelector("tr:nth-child(2) > td:nth-child(2)")))).getScreenshotAs(OutputType.FILE);
        Files.move(file2.toPath(), Path.of("src/main/resources/screenshots/screenshot2.png"), StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void testListTexts(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/grid/index.html");
        var items = driver.findElements(By.tagName("li"));

        List<String> texts = items.stream().map(WebElement::getText).toList();

        assertThat(texts)
                .hasSize(4)
                .containsExactly("One", "Two", "Three", "Four");
    }

    @Test
    void testListNames(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/components/index.html");
        var items = driver.findElements(By.cssSelector("td:nth-child(2)"));

        assertThat(items)
                .hasSize(3)
                .extracting(WebElement::getText)
                .containsExactly("John Doe", "Jane Doe", "Jack Doe");
    }

    @Test
    void testconvertToEmployees(WebDriver driver) {
        var employees = getEmployeesFromTable(driver);

        assertThat(employees)
                .extracting(Employee::getName, Employee::getYearOfBirth)
                .containsExactly(
                        tuple("John Doe", 1970),
                        tuple("Jane Doe", 1980),
                        tuple("Jack Doe", 1990));
    }

    List<Employee> getEmployeesFromTable(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/components/index.html");
        var rows = driver.findElements(By.tagName("tr"));
        var employees = new ArrayList<Employee>();
        for (int i = 1; i < rows.size(); i++) {
            var row = rows.get(i);
            var cells = row.findElements(By.tagName("td"));
            long id = Long.parseLong(cells.get(0).getText());
            String name = cells.get(1).getText();
            int yearOfBirth = Integer.parseInt(cells.get(2).getText());
            employees.add(new Employee(id, name, yearOfBirth));
        }
        return employees;
    }

    @Test
    void testInputField(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/components/index.html");
        var input = driver.findElement(By.name("text"));
        input.sendKeys("hello input");

        assertEquals("hello input", input.getDomProperty("value"));
    }

    @Test
    void testCheckBox(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/components/index.html");
        var checkbox = driver.findElement(By.name("checkbox"));
//        checkbox.click();
        var label = driver.findElement(By.cssSelector("[for='checkbox1']"));
        label.click();

        assertTrue(checkbox.isSelected());

        assertTrue(driver.findElement(By.name("disabled-checkbox")).isEnabled());
    }

    @Test
    void testRadioButtons(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/components/index.html");
        driver.findElement(By.id("radiobtn1")).click();
        var count = driver
                .findElements(By.cssSelector("input[type=radio]"))
                .stream()
                .filter(we -> we.isSelected())
                .count();

        assertEquals(1, count);
    }

    @Test
    void testSelect(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/components/index.html");
        var select = new Select(driver.findElement(By.id("dropdown")));
        select.selectByValue("option3");

        assertEquals("Option 3", select.getFirstSelectedOption().getText());
    }

    //    @Test
    @RepeatedTest(15)
    void testTraining(WebDriver driver) {
        driver.get("https://training360.com");
        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var modal = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("NewsletterModal"))));

        // Felesleges, hiszen a wait úgyis TimeoutException-t dobna, ha nem jelenne meg
        assertTrue(modal.isDisplayed());

//        modal.findElement(By.id("NewsletterModalCloseButton")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("NewsletterModalCloseButton"))).click();

        wait.until(ExpectedConditions.invisibilityOf(modal));

        // Felesleges, hiszen a wait úgyis TimeoutException-t dobna, ha nem tűnne el
        assertFalse(modal.isDisplayed());
    }

    @Test
    void testTimeAlert(WebDriver driver) {
        driver.get("http://127.0.0.1:5500/messages/index.html");
        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.id("liveAlertTimeoutBtn")).click();
        var alert = wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.id("liveAlertPlaceholder"))
        ));

//        assertTrue(alert.isDisplayed());
        assertTrue(alert.getText().startsWith("Nice"));

        alert.findElement(By.cssSelector("button.btn-close")).click();

        assertFalse(alert.isDisplayed());
    }
}
