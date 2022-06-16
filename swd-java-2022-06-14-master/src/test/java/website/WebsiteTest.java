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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Teszt osztály
@Slf4j
@SeleniumTest
@ExtendWith(LoggingExtension.class)
class WebsiteTest {

//    private static final Logger log = LoggerFactory.getLogger(WebsiteTest.class);

    // Teszteset
    @Test
    void testSearch(WebDriver driver) {
        // Given
        driver.get("https://www.python.org");

        // When
        driver.findElement(By.id("id-search-field")).click();
        driver.findElement(By.id("id-search-field")).sendKeys("testing");
        driver.findElement(By.id("submit")).click();
        log.debug("Click on GO button");

        // Then
        String result = driver.findElement(By.cssSelector("h3:nth-child(2)")).getText();
        assertEquals("Results", result);
    }

    @Test
    void testPsf(WebDriver driver) {
        driver.get("https://www.python.org");
        driver.findElement(By.linkText("PSF")).click();
        log.debug("Click on PDF menu item");
        assertEquals("Python Software Foundation", driver.getTitle());
    }

}
