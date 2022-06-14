package website;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
